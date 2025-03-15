package com.pablosgon.mortismaycry.webapi.clients;

import java.net.http.HttpResponse;

public interface BSClient {

    public HttpResponse<String> getClub(String clubTag);
    public HttpResponse<String> getPlayer(String playerTag);

}