package com.pablosgon.mortismaycry.webapi.clients;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BSClientImpl implements BSClient {

    private final HttpClient httpClient;

    private final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjA5OWY3ZmYyLWY4ZjgtNGI2MC1hMjViLTg1ZGRhOWEzMzJiMiIsImlhdCI6MTc0MjA1ODQ3OSwic3ViIjoiZGV2ZWxvcGVyL2IyMDIwNTgwLThhMjktYzlhZS1jMGM3LWJhNzhiNDVjMjdmMyIsInNjb3BlcyI6WyJicmF3bHN0YXJzIl0sImxpbWl0cyI6W3sidGllciI6ImRldmVsb3Blci9zaWx2ZXIiLCJ0eXBlIjoidGhyb3R0bGluZyJ9LHsiY2lkcnMiOlsiNjIuODMuMjE1LjYyIl0sInR5cGUiOiJjbGllbnQifV19.EmuBrkAicq2RhZ-LaPPXMRn1CV7OelSVhakdI5xaqD3JUgw7YVfmIn92Md9iZ1wlJyjxOG97cpuF2OaKjJJkGg";

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
