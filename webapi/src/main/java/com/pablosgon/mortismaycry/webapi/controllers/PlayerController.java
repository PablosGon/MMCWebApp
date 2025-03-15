package com.pablosgon.mortismaycry.webapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pablosgon.mortismaycry.webapi.business.PlayerBusiness;
import com.pablosgon.mortismaycry.webapi.models.Player;

@RestController
@CrossOrigin
public class PlayerController {
    
    private PlayerBusiness business;

    public PlayerController(PlayerBusiness business) {
        this.business = business;
    }

    @GetMapping("/player/{playerTag}")
    public ResponseEntity<Player> getClub(@PathVariable String playerTag){
        try {
            Player player = business.getPlayer(playerTag);

            if(player != null) {
                return ResponseEntity.ok(player);
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