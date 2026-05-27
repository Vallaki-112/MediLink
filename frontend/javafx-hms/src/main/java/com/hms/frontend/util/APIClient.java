package com.hms.frontend.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIClient {

    // Your active Render Deployment URL 
    private static final String BASE_URL = "https://medilink-06px.onrender.com";

    private static final HttpClient client = HttpClient.newHttpClient();

    // =========================================================================
    // HTTP GET
    // =========================================================================
    public static String get(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // =========================================================================
    // HTTP POST
    // =========================================================================
    public static String post(String endpoint, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json") // Tells backend we are sending JSON data 
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // =========================================================================
    // HTTP PUT
    // =========================================================================
    public static String put(String endpoint, String jsonBody) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json") // Tells backend we are updating with JSON data 
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // =========================================================================
    // HTTP DELETE
    // =========================================================================
    public static String delete(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .DELETE()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}