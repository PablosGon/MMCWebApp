package com.pablosgon.mortismaycry.webapi.business;

import java.net.http.HttpResponse;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.constants.ClubConstants;
import com.pablosgon.mortismaycry.webapi.entities.models.Player;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSClub;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAPlayer;
import com.pablosgon.mortismaycry.webapi.exceptions.NotFoundException;
import com.pablosgon.mortismaycry.webapi.repositories.PlayerRepository;

public class PlayerBusinessImpl implements PlayerBusiness {

    private BSClient client;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private PlayerRepository playerRepository;

    public PlayerBusinessImpl(
        BSClient client,
        ModelMapper modelMapper,
        ObjectMapper objectMapper,
        PlayerRepository playerRepository
    ) {
        this.client = client;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.playerRepository = playerRepository;
    }

    @Override
    public Player getPlayer(String tag) throws Exception {
        if(tag == null) {
            throw new IllegalArgumentException("player tag must not be null");
        }

        System.out.println("Getting Player " + tag);

        Player player;

        try {
            HttpResponse<String> response = client.getPlayer(tag);
            BSPlayer bsPlayer = objectMapper.readValue(response.body(), BSPlayer.class);
            JPAPlayer jpaPlayer = playerRepository.findPlayerByTag(tag);
            player = modelMapper.map(bsPlayer, Player.class);

            if(jpaPlayer == null) {
                throw new NotFoundException();
            }

            modelMapper.map(jpaPlayer, player);
            System.out.println("Get Player successful: " + response.body());
        } catch (Exception e) {
            throw e;
        }

        return player;
    }

    @Override
    public Player createPlayer(String tag) throws Exception {
        if(tag == null || notInClub(tag)) {
            throw new IllegalArgumentException();
        }

        System.out.println("Creating player with tag " + tag);

        Player player;

        try {
            JPAPlayer jpaPlayer = new JPAPlayer();
            jpaPlayer.setTag(tag);
    
            jpaPlayer = playerRepository.save(jpaPlayer);
            
            HttpResponse<String> response = client.getPlayer(tag);
            BSPlayer bsPlayer = objectMapper.readValue(response.body(), BSPlayer.class);
            player = modelMapper.map(bsPlayer, Player.class);
        } catch (Exception e) {
            throw e;
        }

        return player;
    }
    
    //#region

    private boolean notInClub(String tag) throws Exception {
        boolean inClub = false;
        
        try {
            String[] clubIds = ClubConstants.CLUB_IDS;
            for (String id : clubIds) {
                BSClub club = objectMapper.readValue(client.getClub(id).body(), BSClub.class);
                if(playerInBSClub(tag, club)) {
                    inClub = true;
                    continue;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        
        return !inClub;
    }

    private boolean playerInBSClub(String playerTag, BSClub club) {
        return club.getMembers().stream().anyMatch(x -> x.getTag().replace("#", "").equals(playerTag));
    }

    //#endregion
    
}
