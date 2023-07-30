package com.belvopoc.belvopoc.service;


import com.belvopoc.belvopoc.api.belvo.*;
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

    private final JwtService jwtService;
    private final UserService userService;
    private final InstitutionService institutionService;
    private final BelvoLinkRepository belvoLinkRepository;
    private final BelvoHttpService belvoHttpService;

    // TODO: look for a way to store links encoded, finding a way to consult the Belvo API afterwards
    // It's supposed the jwt will be present and valid due to Spring security filters
    @Override
    public CreateLinkResponse createLink(CreateLinkRequest request, HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        User user = userService.findByEmail(userEmail);
        String institutionName = request.getInstitution();
        Institution institution = institutionService.getInstitutionByName(institutionName);
        String bankUsername = request.getBankUsername();
        String bankPassword = request.getBankPassword();

        if (institution == null || bankUsername == null || bankPassword == null) {
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
    public GetAccountsResponse getAccounts(GetAccountsRequest request, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public GetTransactionsResponse getTransactions(GetTransactionsRequest request, HttpServletRequest httpServletRequest) {
        return null;
    }

}
