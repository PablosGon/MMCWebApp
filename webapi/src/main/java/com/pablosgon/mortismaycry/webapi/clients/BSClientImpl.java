package com.pablosgon.mortismaycry.webapi.clients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;

import com.pablosgon.mortismaycry.webapi.exceptions.BsForbiddenException;
import com.pablosgon.mortismaycry.webapi.exceptions.BsNotFoundException;

public class BSClientImpl implements BSClient {

    private final HttpClient httpClient;

    @Value("${app.bs.api.key}")
    private String apiKey;

    private String apiUrl = "https://api.brawlstars.com/v1/";

    public BSClientImpl(HttpClient webClient) {
        this.httpClient = webClient;
    }

    @Override
    public HttpResponse<String> getClub(String clubTag) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl + "clubs/%23" + clubTag))
            .header("Authorization", "Bearer " + apiKey)
            .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            throw new BsNotFoundException();
        } else if (response.statusCode() == 403) {
            throw new BsForbiddenException();
        }

        return response;
    }

    @Override
    public HttpResponse<String> getPlayer(String playerTag) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl + "players/%23" + playerTag))
            .header("Authorization", "Bearer " + apiKey)
            .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            throw new BsNotFoundException();
        } else if (response.statusCode() == 403) {
            throw new BsForbiddenException();
        }

        return response;
    }
    
}
