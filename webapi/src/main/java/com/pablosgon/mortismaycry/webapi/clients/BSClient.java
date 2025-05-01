package com.pablosgon.mortismaycry.webapi.clients;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface BSClient {

    public HttpResponse<String> getClub(String clubTag) throws IOException, InterruptedException;
    public HttpResponse<String> getPlayer(String playerTag) throws IOException, InterruptedException;

}