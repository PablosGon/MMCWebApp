package com.pablosgon.mortismaycry.webapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.models.bs.BSClub;

import java.net.http.HttpResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin
public class ClubController {
    
    private BSClient bsclient;

    public ClubController(BSClient bsClient) {
        this.bsclient = bsClient;
    }

    @GetMapping("/club/{clubTag}")
    public BSClub GetClub(@PathVariable String clubTag){
        BSClub club;
        HttpResponse<String> response = bsclient.getClub(clubTag);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        try {
            club = om.readValue(response.body(), BSClub.class);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage() + "{\"tag\":\"#2jr9qvrgp\",\"name\":\"Mortis May Cry\"}" + response.body());
        }

        return club;
    }

}