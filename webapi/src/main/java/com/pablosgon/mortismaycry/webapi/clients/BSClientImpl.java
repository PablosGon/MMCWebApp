package com.pablosgon.mortismaycry.webapi.clients;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;

public class BSClientImpl implements BSClient {

    private final HttpClient httpClient;

    @Value("${app.bs.api.key}")
    private String API_KEY;

    public BSClientImpl(HttpClient webClient) {
        this.httpClient = webClient;
    }

    @Override
    public HttpResponse<String> getClub(String clubTag) {
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.brawlstars.com/v1/clubs/%23" + clubTag))
            .header("Authorization", "Bearer " + API_KEY)
            .build();
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch(Exception e) {
            throw new RuntimeException("Cagamos");
        }
        return response;
    }

    @Override
    public HttpResponse<String> getPlayer(String playerTag) {
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.brawlstars.com/v1/players/%23" + playerTag))
            .header("Authorization", "Bearer " + API_KEY)
            .build();
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch(Exception e) {
            throw new RuntimeException("Cagamos");
        }
        return response;
    }
    
}
