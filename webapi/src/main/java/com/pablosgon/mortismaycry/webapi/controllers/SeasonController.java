package com.pablosgon.mortismaycry.webapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.pablosgon.mortismaycry.webapi.business.SeasonBusiness;
import com.pablosgon.mortismaycry.webapi.models.entities.Season;
import com.pablosgon.mortismaycry.webapi.models.requests.CreateSeasonRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin
public class SeasonController {
    
    private SeasonBusiness business;

    public SeasonController(SeasonBusiness business) {
        this.business = business;
    }

    @GetMapping("/seasons")
    public ResponseEntity<List<Season>> getSeasons() {
        List<Season> seasons = business.getSeasons();

        if(seasons != null) {
            return ResponseEntity.ok(seasons);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/season")
    public ResponseEntity<Integer> postSeason(@RequestBody CreateSeasonRequest request) {
        business.createSeason(request);
        return ResponseEntity.ok(request.getId());
    }
    

}
