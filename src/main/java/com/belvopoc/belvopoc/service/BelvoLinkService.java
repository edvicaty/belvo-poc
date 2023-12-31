package com.belvopoc.belvopoc.service;

import com.belvopoc.belvopoc.model.dto.*;
import jakarta.servlet.http.HttpServletRequest;

public interface BelvoLinkService {

    CreateLinkResponse createLink(CreateLinkRequest request, HttpServletRequest httpServletRequest);

    AccountsResponse[] getAccounts(AccountsRequest request, HttpServletRequest httpServletRequest);

    TransactionsResponse[] getTransactions(TransactionsRequest request, HttpServletRequest httpServletRequest);

}
