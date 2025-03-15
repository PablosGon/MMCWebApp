package com.pablosgon.mortismaycry.webapi.business;

import java.net.http.HttpResponse;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.models.Player;
import com.pablosgon.mortismaycry.webapi.models.bs.BSPlayer;

public class PlayerBusinessImpl implements PlayerBusiness {

    private BSClient client;
    private ModelMapper mapper;
    private ObjectMapper objectMapper;

    public PlayerBusinessImpl(BSClient client, ModelMapper mapper, ObjectMapper objectMapper) {
        this.client = client;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Player getPlayer(String tag) {
        if(tag == null) {
            throw new IllegalArgumentException("player tag must not be null");
        }

        Player player;

        try {
            HttpResponse<String> response = client.getPlayer(tag);
            BSPlayer bsPlayer = objectMapper.readValue(response.body(), BSPlayer.class);
            player = mapper.map(bsPlayer, Player.class);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return player;
    }
    
}
