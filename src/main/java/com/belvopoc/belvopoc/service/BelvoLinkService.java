package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.api.belvo.*;
import jakarta.servlet.http.HttpServletRequest;

public interface BelvoLinkService {

    CreateLinkResponse createLink(CreateLinkRequest request, HttpServletRequest httpServletRequest);

    GetAccountsResponse getAccounts(GetAccountsRequest request, HttpServletRequest httpServletRequest);

    GetTransactionsResponse getTransactions(GetTransactionsRequest request, HttpServletRequest httpServletRequest);

}
