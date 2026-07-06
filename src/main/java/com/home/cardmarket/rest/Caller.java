package com.home.cardmarket.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.home.cardmarket.exception.BuildingRequestException;
import com.home.cardmarket.exception.HtmlParseException;
import com.home.cardmarket.exception.SendingRequestException;
import com.home.cardmarket.exception.UnexpectedException;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class Caller {

    public Document call(String url) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            return parseHtml(sendRequest(client, buildRequest(url)).body());
        } catch (BuildingRequestException | SendingRequestException | HtmlParseException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while calling URL: {}", url, e);
            throw new UnexpectedException();
        }
    }

    private HttpResponse<String> sendRequest(HttpClient client, HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("Successfully sent request and got response headers: {}", response.headers());
            log.debug("Successfully sent request and got response body: {}", response.body());
            return response;
        } catch (Exception e) {
            log.error("Failed to send request to URL: {}", request.uri(), e);
            throw new SendingRequestException();
        }
    }

    private HttpRequest buildRequest(String url) {
        try {
            return HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "text/html")
                    .GET()
                    .build();
        } catch (Exception e) {
            log.error("Failed to build request for URL: {}", url, e);
            throw new BuildingRequestException();
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
