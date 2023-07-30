package com.belvopoc.belvopoc.service;


import com.belvopoc.belvopoc.api.belvo.CreateLinkRequest;
import com.belvopoc.belvopoc.api.belvo.CreateLinkResponse;
import com.belvopoc.belvopoc.domain.BelvoLink;
import com.belvopoc.belvopoc.domain.Institution;
import com.belvopoc.belvopoc.domain.User;
import com.belvopoc.belvopoc.repository.BelvoLinkRepository;
import com.belvopoc.belvopoc.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BelvoLinkServiceImpl implements BelvoLinkService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final InstitutionService institutionService;
    private final BelvoLinkRepository belvoLinkRepository;

    // TODO: look for a way to store links encoded, finding a way to consult the Belvo API afterwards
    // It's supposed the jwt will be present and valid due to Spring security filters
    @Override
    public CreateLinkResponse createLink(CreateLinkRequest request, HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        User user = userRepository.findByEmail(userEmail);
        String institutionName = request.getInstitution();
        Institution institution = institutionService.getInstitutionByName(institutionName);

        if (institution == null) {
            return null;
        }

        // TODO: add logic to create a new Belvo Link via API and return if request failed


        var belvoLink = BelvoLink.builder()
                .user(user)
                .institution(institution)
                .belvoId("Belvo Id String")
                .build();

        belvoLinkRepository.save(belvoLink);

        return CreateLinkResponse.builder()
                .status(true)
                .build();
    }

}
