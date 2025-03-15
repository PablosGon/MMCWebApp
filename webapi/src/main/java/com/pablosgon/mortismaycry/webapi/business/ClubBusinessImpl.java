package com.pablosgon.mortismaycry.webapi.business;

import java.net.http.HttpResponse;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.models.Club;
import com.pablosgon.mortismaycry.webapi.models.bs.BSClub;

public class ClubBusinessImpl implements ClubBusiness {
    
    private BSClient client;
    private ModelMapper mapper;
    private ObjectMapper objectMapper;

    public ClubBusinessImpl(BSClient bsClient, ModelMapper mapper, ObjectMapper objectMapper) {
        this.client = bsClient;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Club getClub(String tag) {
        if(tag == null) {
            throw new IllegalArgumentException("club tag must not be null");
        }

        Club club;

        try {
            HttpResponse<String> response = client.getClub(tag);
            BSClub bsClub = objectMapper.readValue(response.body(), BSClub.class);
            club = mapper.map(bsClub, Club.class);
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException(e.getMessage());
        }

        return club;
    }

}
