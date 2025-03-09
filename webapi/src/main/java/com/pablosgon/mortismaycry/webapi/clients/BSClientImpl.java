package com.pablosgon.mortismaycry.webapi.clients;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BSClientImpl implements BSClient {

    private final HttpClient httpClient;

    public BSClientImpl(HttpClient webClient) {
        this.httpClient = webClient;
    }

    @Override
    public HttpResponse<String> getClub(String clubTag) {
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.brawlstars.com/v1/clubs/%23" + clubTag))
            .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjI1ZTgwMGU0LTY3ODgtNGNmMi1hNzRiLTE2YTk0YTlmNGJlNiIsImlhdCI6MTc0MTEwMzgyNSwic3ViIjoiZGV2ZWxvcGVyL2IyMDIwNTgwLThhMjktYzlhZS1jMGM3LWJhNzhiNDVjMjdmMyIsInNjb3BlcyI6WyJicmF3bHN0YXJzIl0sImxpbWl0cyI6W3sidGllciI6ImRldmVsb3Blci9zaWx2ZXIiLCJ0eXBlIjoidGhyb3R0bGluZyJ9LHsiY2lkcnMiOlsiNS4yMjUuMjEyLjE1NSJdLCJ0eXBlIjoiY2xpZW50In1dfQ.-8lViD1aav8l6xi9KkbV8VD89e_ww8Ue4MRT40igAl3Wsuf6hjf_P9_FPsGJZPQJ9iBc3VNZORHHSweDPu32eQ")
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
