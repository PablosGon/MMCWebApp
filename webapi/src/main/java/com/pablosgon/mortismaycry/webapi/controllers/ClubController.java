package com.pablosgon.mortismaycry.webapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.pablosgon.mortismaycry.webapi.business.ClubBusiness;
import com.pablosgon.mortismaycry.webapi.entities.models.Club;
import com.pablosgon.mortismaycry.webapi.entities.requests.NewMegapigRequest;
import com.pablosgon.mortismaycry.webapi.entities.requests.UpdateMembersRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@CrossOrigin
public class ClubController {
    
    private ClubBusiness business;

    public ClubController(ClubBusiness business) {
        this.business = business;
    }

    @GetMapping("/club/{clubTag}")
    public ResponseEntity<Club> getClub(@PathVariable String clubTag){
        Club club = business.getClub(clubTag);

        if(club != null) {
            return new ResponseEntity<>(club, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateMembers")
    public ResponseEntity<Club> updateMembers(@RequestBody UpdateMembersRequest request) {
        business.updateMembers(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/club/newMegaPig")
    public ResponseEntity<String> newMegaPig(@RequestBody NewMegapigRequest request) {
        business.newMegapigReport(request);
        return ResponseEntity.ok().build();
    }

}