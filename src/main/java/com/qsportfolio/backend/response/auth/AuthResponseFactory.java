package com.qsportfolio.backend.response.auth;

import com.qsportfolio.backend.domain.transaction.Transaction;
import com.qsportfolio.backend.response.stock.StockResponseFactory;
import com.qsportfolio.backend.response.transaction.TransactionResponse;

public class AuthResponseFactory {
    private AuthResponseFactory() {}

    public static RegisterResponse createRegisterSuccessResponse() {
        return new RegisterResponse(
                "success",
                "User registered successfully!"
        );
    }

    public static RegisterResponse createRegisterFailureResponse(String message) {
        return new RegisterResponse(
                "error",
                message
        );
    }
}
