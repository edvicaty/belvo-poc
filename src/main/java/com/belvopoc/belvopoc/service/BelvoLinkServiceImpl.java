package com.belvopoc.belvopoc.service;


import com.belvopoc.belvopoc.model.BelvoLink;
import com.belvopoc.belvopoc.model.Institution;
import com.belvopoc.belvopoc.model.User;
import com.belvopoc.belvopoc.model.dto.*;
import com.belvopoc.belvopoc.repository.BelvoLinkRepository;
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
            throw new IllegalArgumentException("Institution not found");
        }
        Institution institution = institutionService.getInstitutionByName(institutionName);
        String bankUsername = request.getBankUsername();
        String bankPassword = request.getBankPassword();

        if (institution == null || bankUsername == null || bankPassword == null) {
            throw new IllegalArgumentException("Error handling banking credentials");
        }

        // Avoid duplicated Links creations for the same pair User/Institution
        BelvoLink existingBelvoLink = belvoLinkRepository.findByInstitutionIdAndUserId(institution.getId(), user.getId());
        if (existingBelvoLink != null) {
            throw new IllegalArgumentException("Link is already created for that User Institution pair");
        }
        RegisterBelvoLinkResponse registerBelvoLinkResponse = belvoHttpService.registerBelvoLink(
                institutionName,
                bankUsername,
                bankPassword
        );

        if (registerBelvoLinkResponse == null || !institutionName.equals(registerBelvoLinkResponse.getInstitution())) {
            throw new IllegalArgumentException("Error registering link on Belvo API");
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
            throw new IllegalArgumentException("Could not find Belvo Link");
        }
        String belvoLinkId = belvoLink.getBelvoId();
        if (belvoLinkId == null) {
            throw new IllegalArgumentException("Missing Belvo Link Id");
        }
        return belvoHttpService.getAccountsByLink(belvoLinkId);
    }

    @Override
    public TransactionsResponse[] getTransactions(TransactionsRequest request, HttpServletRequest httpServletRequest) {
        BelvoLink belvoLink = getBelvoLinkByRequest(httpServletRequest, request.getInstitution());

        if (belvoLink == null) {
            throw new IllegalArgumentException("Could not find Belvo Link");
        }

        String belvoLinkId = belvoLink.getBelvoId();
        if (belvoLinkId == null) {
            throw new IllegalArgumentException("Missing Belvo Link Id");
        }

        return belvoHttpService.getTransactionsByLink(belvoLinkId, request.getDateFrom(), request.getDateTo());
    }

    private BelvoLink getBelvoLinkByRequest(HttpServletRequest httpServletRequest, String institutionName) {
        if (institutionName == null) {
            throw new IllegalArgumentException("institutionName is required");

        }
        String authHeader = httpServletRequest.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        User user = userService.findByEmail(userEmail);
        Institution institution = institutionService.getInstitutionByName(institutionName);

        if (institution == null) {
            throw new IllegalArgumentException("Could not find and institution with that name");
        }
        return belvoLinkRepository.findByInstitutionIdAndUserId(institution.getId(), user.getId());
    }

}
