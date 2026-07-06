package com.home.cardmarket.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FailedToLaunchBrowser extends BaseException {

    public FailedToLaunchBrowser(String message) {
        super(message);
    }

    public FailedToLaunchBrowser(String message, Throwable cause) {
        super(message, cause);
    }
}
