package com.belvopoc.belvopoc.service;


import com.belvopoc.belvopoc.api.belvo.dto.*;
import com.belvopoc.belvopoc.domain.BelvoLink;
import com.belvopoc.belvopoc.domain.Institution;
import com.belvopoc.belvopoc.domain.User;
import com.belvopoc.belvopoc.repository.BelvoLinkRepository;
import com.belvopoc.belvopoc.service.auth.JwtService;
import com.belvopoc.belvopoc.service.dto.BelvoHttpService;
import com.belvopoc.belvopoc.service.dto.RegisterBelvoLinkResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BelvoLinkServiceImpl implements BelvoLinkService {

    private final BelvoLinkRepository belvoLinkRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final InstitutionService institutionService;
    private final BelvoHttpService belvoHttpService;

    // TODO: look for a way to store links encoded, finding a way to consult the Belvo API afterwards
    // The jwt and user should be present and valid due to Spring security filters
    @Override
    public CreateLinkResponse createLink(CreateLinkRequest request, HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        User user = userService.findByEmail(userEmail);
        String institutionName = request.getInstitution();
        if (institutionName == null) {
            return null;
        }
        Institution institution = institutionService.getInstitutionByName(institutionName);
        String bankUsername = request.getBankUsername();
        String bankPassword = request.getBankPassword();

        if (institution == null || bankUsername == null || bankPassword == null) {
            return null;
        }

        // Avoid duplicated Links creations for the same pair User/Institution
        BelvoLink existingBelvoLink = belvoLinkRepository.findByInstitutionIdAndUserId(institution.getId(), user.getId());
        if (existingBelvoLink != null) {
            return null;
        }
        RegisterBelvoLinkResponse registerBelvoLinkResponse = belvoHttpService.registerBelvoLink(
                institutionName,
                bankUsername,
                bankPassword
        );

        if (registerBelvoLinkResponse == null || !institutionName.equals(registerBelvoLinkResponse.getInstitution())) {
            return null;
        }

        var belvoLink = BelvoLink.builder()
                .user(user)
                .institution(institution)
                .belvoId(registerBelvoLinkResponse.getId())
                .build();

        belvoLinkRepository.save(belvoLink);

        return CreateLinkResponse.builder()
                .status(true)
                .build();
    }

    @Override
    public AccountsResponse[] getAccounts(AccountsRequest request, HttpServletRequest httpServletRequest) {
        BelvoLink belvoLink = getBelvoLinkByRequest(httpServletRequest, request.getInstitution());
        if (belvoLink == null) {
            return null;
        }
        String belvoLinkId = belvoLink.getBelvoId();
        if (belvoLinkId == null) {
            return null;
        }
        return belvoHttpService.getAccountsByLink(belvoLinkId);
    }

    @Override
    public TransactionsResponse[] getTransactions(TransactionsRequest request, HttpServletRequest httpServletRequest) {
        BelvoLink belvoLink = getBelvoLinkByRequest(httpServletRequest, request.getInstitution());
        System.out.println("1-----------------------------------");

        if (belvoLink == null) {
            return null;
        }
        System.out.println("2-----------------------------------");

        String belvoLinkId = belvoLink.getBelvoId();
        if (belvoLinkId == null) {
            return null;
        }
        System.out.println("3-----------------------------------");

        return belvoHttpService.getTransactionsByLink(belvoLinkId);
    }

    private BelvoLink getBelvoLinkByRequest(HttpServletRequest httpServletRequest, String institutionName) {
        if (institutionName == null) {
            return null;
        }
        String authHeader = httpServletRequest.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        User user = userService.findByEmail(userEmail);
        Institution institution = institutionService.getInstitutionByName(institutionName);
        System.out.println("4-----------------------------------");
        System.out.println(jwt);
        System.out.println(userEmail);
        System.out.println(institutionName);
        System.out.println(institution.getName());

        if (institution == null) {
            return null;
        }
        return belvoLinkRepository.findByInstitutionIdAndUserId(institution.getId(), user.getId());
    }

}
