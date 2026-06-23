package com.home.cardmarket.caller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlCaller {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private UrlCaller() {
    }

    public static <T> T getByUrl(String url, Class<T> clazz) {
        log.debug("Calling URL: {}", url);

        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL must not be empty");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("Response class must not be null");
        }

        HttpRequest request = HttpRequest.newBuilder(URI.create(url.trim()))
                .timeout(REQUEST_TIMEOUT)
                .header("Accept", "application/json,text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .GET()
                .build();

        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode < 200 || statusCode >= 300) {
                throw new IllegalStateException("GET " + url + " failed with HTTP status " + statusCode);
            }

            T result = convertResponse(response.body(), clazz, url);
            log.debug("GET {} returned: {}", url, result);
            return result;
        } catch (IOException e) {
            throw new IllegalStateException("GET " + url + " failed", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("GET " + url + " was interrupted", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid URL: " + url, e);
        }
    }

    private static <T> T convertResponse(String body, Class<T> clazz, String url) {
        if (String.class.equals(clazz)) {
            return clazz.cast(body);
        }

        if (Document.class.equals(clazz)) {
            return clazz.cast(Jsoup.parse(body));
        }

        try {
            return OBJECT_MAPPER.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not convert GET " + url + " response to " + clazz.getName(), e);
        }
    }
}
