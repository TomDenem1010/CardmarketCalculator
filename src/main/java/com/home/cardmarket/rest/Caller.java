package com.home.cardmarket.rest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.home.cardmarket.exception.FailedToLaunchBrowser;
import com.home.cardmarket.exception.HtmlParseException;
import com.home.cardmarket.exception.UnexpectedException;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class Caller {

    public Document callWithPlaywright(String url, Browser browser) {
        try {
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate(url);
            page.waitForLoadState(LoadState.NETWORKIDLE);

            String html = page.content();

            log.debug("Successfully called URL with Playwright and got HTML content: {}", html);

            return parseHtml(html);
        } catch (HtmlParseException | FailedToLaunchBrowser e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while calling URL: {}", url, e);
            throw new UnexpectedException();
        }
    }

    private Document parseHtml(String html) {
        try {
            Document document = Jsoup.parse(html);
            log.debug("Successfully parsed HTML document: {}", document.toString());
            return document;
        } catch (Exception e) {
            log.error("Failed to parse HTML", e);
            throw new HtmlParseException();
        }
    }
}
