package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.api.belvo.CreateLinkRequest;
import com.belvopoc.belvopoc.api.belvo.CreateLinkResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface BelvoLinkService {

    CreateLinkResponse createLink(CreateLinkRequest request, HttpServletRequest httpServletRequest);

}
