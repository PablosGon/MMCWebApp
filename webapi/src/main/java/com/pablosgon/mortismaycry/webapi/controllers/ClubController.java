package com.pablosgon.mortismaycry.webapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.pablosgon.mortismaycry.webapi.business.ClubBusiness;
import com.pablosgon.mortismaycry.webapi.models.entities.Club;
import com.pablosgon.mortismaycry.webapi.models.requests.CreateMegapigReportRequest;
import com.pablosgon.mortismaycry.webapi.models.requests.UpdateMembersRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;




@RestController
@CrossOrigin
public class ClubController {
    
    private ClubBusiness business;

    public ClubController(ClubBusiness business) {
        this.business = business;
    }

    @GetMapping("/club/{clubTag}")
    public ResponseEntity<Club> getClub(@PathVariable String clubTag, @RequestHeader String isAdmin){
        Club club = business.getClub(clubTag, Boolean.parseBoolean(isAdmin));

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

    @PostMapping("/reportMegapig")
    public ResponseEntity<String> createMegapigReport(@RequestBody CreateMegapigReportRequest request, @RequestHeader String isAdmin) {

        if (!Boolean.parseBoolean(isAdmin)) {
            return ResponseEntity.badRequest().build();
        }

        business.createMegapigReport(request);
        return ResponseEntity.ok().build();
    }

}