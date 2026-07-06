package com.home.cardmarket.rest;

import java.util.Objects;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import lombok.Getter;

@Getter
public class PlaywrightBrowserContext {

    private final Playwright playwright;
    private final Browser browser;

    public PlaywrightBrowserContext() {
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().connectOverCDP("http://localhost:9222");
    }

    public void close() {
        if (Objects.nonNull(browser)) {
            browser.close();
        }

        if (Objects.nonNull(playwright)) {
            playwright.close();
        }
    }
}
