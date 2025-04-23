package com.pablosgon.mortismaycry.webapi.business;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.constants.ClubConstants;
import com.pablosgon.mortismaycry.webapi.entities.models.ClubMember;
import com.pablosgon.mortismaycry.webapi.entities.models.Player;
import com.pablosgon.mortismaycry.webapi.entities.models.StarBadgeCase;
import com.pablosgon.mortismaycry.webapi.entities.models.StarLegend;
import com.pablosgon.mortismaycry.webapi.entities.models.StarMaster;
import com.pablosgon.mortismaycry.webapi.entities.models.StarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.StarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSClub;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarLegend;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarMaster;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.exceptions.NotFoundException;
import com.pablosgon.mortismaycry.webapi.repositories.PlayerRepository;
import com.pablosgon.mortismaycry.webapi.repositories.StarPlayerRepository;

public class PlayerBusinessImpl implements PlayerBusiness {

    private BSClient client;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private PlayerRepository playerRepository;
    private StarPlayerRepository starPlayerRepository;

    public PlayerBusinessImpl(
        BSClient client,
        ModelMapper modelMapper,
        ObjectMapper objectMapper,
        PlayerRepository playerRepository,
        StarPlayerRepository starPlayerRepository
    ) {
        this.client = client;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.playerRepository = playerRepository;
        this.starPlayerRepository = starPlayerRepository;
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
            mapStarBadgesToPlayer(player);
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

    private void mapStarBadgesToPlayer(Player player) {
        List<JPAStarPlayer> starPlayers = starPlayerRepository.findByPlayerTag(player.getTag());
        mapStarBadges(player, starPlayers);
    }

    private void mapStarBadges(Player player, List<JPAStarPlayer> badges) {
        List<StarWeekPlayer> weeklyBadges = new ArrayList<>();
        List<StarSeasonPlayer> grandBadges = new ArrayList<>();
        List<StarLegend> legendBadges = new ArrayList<>();
        List<StarMaster> masterBadges = new ArrayList<>();

        for (JPAStarPlayer jpaStarPlayer : badges) {
            if (jpaStarPlayer instanceof JPAStarWeekPlayer) {
                weeklyBadges.add(modelMapper.map(jpaStarPlayer, StarWeekPlayer.class));
            } else if (jpaStarPlayer instanceof JPAStarSeasonPlayer) {
                grandBadges.add(modelMapper.map(jpaStarPlayer, StarSeasonPlayer.class));
            } else if (jpaStarPlayer instanceof JPAStarLegend) {
                legendBadges.add(modelMapper.map(jpaStarPlayer, StarLegend.class));
            } else if (jpaStarPlayer instanceof JPAStarMaster) {
                masterBadges.add(modelMapper.map(jpaStarPlayer, StarMaster.class));
            }
        }

        player.setBadges(new StarBadgeCase(weeklyBadges, grandBadges, legendBadges, masterBadges));
    }

    //#endregion
    
}
