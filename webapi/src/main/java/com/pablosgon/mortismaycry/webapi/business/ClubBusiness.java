package com.pablosgon.mortismaycry.webapi.business;

import java.net.http.HttpResponse;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.models.Club;
import com.pablosgon.mortismaycry.webapi.models.bs.BSClub;

public class ClubBusiness {
    
    private BSClient bsclient;
    private ModelMapper mapper;

    public ClubBusiness(BSClient bsClient, ModelMapper mapper) {
        this.bsclient = bsClient;
        this.mapper = mapper;
    }

    public Club getClub(String tag) {
        if(tag == null) {
            throw new IllegalArgumentException("clubTag must not be null");
        }

        Club club;

        try {
            HttpResponse<String> response = bsclient.getClub(tag);
            ObjectMapper om = new ObjectMapper();
            om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);  
            BSClub bsClub = om.readValue(response.body(), BSClub.class);
            club = mapper.map(bsClub, Club.class);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return club;
    }

}
