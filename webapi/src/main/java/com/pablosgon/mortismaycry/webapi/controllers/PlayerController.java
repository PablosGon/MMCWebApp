package com.pablosgon.mortismaycry.webapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pablosgon.mortismaycry.webapi.business.PlayerBusiness;
import com.pablosgon.mortismaycry.webapi.entities.models.Player;
import com.pablosgon.mortismaycry.webapi.entities.requests.CreatePlayerRequest;
import com.pablosgon.mortismaycry.webapi.exceptions.NotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin
public class PlayerController {
    
    private PlayerBusiness business;

    public PlayerController(PlayerBusiness business) {
        this.business = business;
    }

    @GetMapping("/player/{playerTag}")
    public ResponseEntity<Player> getPlayer(@PathVariable String playerTag){
        try {
            Player player = business.getPlayer(playerTag);

            if(player != null) {
                return ResponseEntity.ok(player);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch(NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/player")
    public ResponseEntity<Player> postPlayer(@RequestBody CreatePlayerRequest request) {
        try {
            Player player = business.createPlayer(request.getPlayerTag());

            if(player != null) {
                return ResponseEntity.ok(player);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch(NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }
    

}