package com.pablosgon.mortismaycry.webapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.pablosgon.mortismaycry.webapi.business.SeasonBusiness;
import com.pablosgon.mortismaycry.webapi.entities.models.Season;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@CrossOrigin
public class SeasonController {
    
    private SeasonBusiness business;

    public SeasonController(SeasonBusiness business) {
        this.business = business;
    }

    @GetMapping("/seasons")
    public ResponseEntity<List<Season>> getMethodName() {
        try {
            List<Season> seasons = business.getSeasons();

            if(seasons != null) {
                return ResponseEntity.ok(seasons);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch(Exception e){
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }
    

}
