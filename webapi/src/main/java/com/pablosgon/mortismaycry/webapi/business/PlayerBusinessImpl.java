package com.pablosgon.mortismaycry.webapi.business;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.constants.ClubConstants;
import com.pablosgon.mortismaycry.webapi.exceptions.BusinessException;
import com.pablosgon.mortismaycry.webapi.models.entities.MegapigRegistry;
import com.pablosgon.mortismaycry.webapi.models.entities.Player;
import com.pablosgon.mortismaycry.webapi.models.entities.StarBadgeCase;
import com.pablosgon.mortismaycry.webapi.models.entities.StarLegend;
import com.pablosgon.mortismaycry.webapi.models.entities.StarMaster;
import com.pablosgon.mortismaycry.webapi.models.entities.StarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.StarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.bs.BSClub;
import com.pablosgon.mortismaycry.webapi.models.entities.bs.BSPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAMegapigRegistry;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarLegend;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarMaster;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.exceptions.BsForbiddenException;
import com.pablosgon.mortismaycry.webapi.exceptions.BsNotFoundException;
import com.pablosgon.mortismaycry.webapi.repositories.PlayerRepository;
import com.pablosgon.mortismaycry.webapi.repositories.StarPlayerRepository;

public class PlayerBusinessImpl implements PlayerBusiness {

    private BSClient client;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private PlayerRepository playerRepository;
    private StarPlayerRepository starPlayerRepository;

    private Logger logger = LoggerFactory.getLogger(PlayerBusinessImpl.class);

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
    public Player getPlayer(String tag) {
        logger.info("Getting Player {}", tag);

        if(tag == null) {
            logger.error("Error validating request: player tag must not be null");
            throw new IllegalArgumentException();
        }

        Player player;

        try {
            HttpResponse<String> response = client.getPlayer(tag);
            JPAPlayer jpaPlayer = playerRepository.findPlayerByTag(tag).orElse(null);

            if (jpaPlayer == null || response.statusCode() == 404) {
                throw new BsNotFoundException();
            } else if (response.statusCode() == 403) {
                throw new BsForbiddenException();
            }

            BSPlayer bsPlayer = objectMapper.readValue(response.body(), BSPlayer.class);
            player = modelMapper.map(bsPlayer, Player.class);
            modelMapper.map(jpaPlayer, player);
            mapMegapigRegistriesToPlayer(jpaPlayer, player);
            mapStarBadgesToPlayer(player);
            logger.info("Successfully retrieved player with tag {}", tag);
        } catch (BsNotFoundException e) {
            logger.error("There was an error while getting the player {}. Player not found", tag);
            throw e;
        } catch (BsForbiddenException e) {
            logger.error("There was an error while getting the player {}. Access to BS API Forbidden. Check API key", tag);
            throw e;
        } catch (InterruptedException e) {
            logger.error("There was an error while getting the player {}. Generic error occured. Task interrupted.", tag);
            Thread.currentThread().interrupt();
            throw new BusinessException();
        } catch (Exception e) {
            logger.error("There was an error while getting the player {}. Generic error occured. Check stack trace", tag);
            throw new BusinessException();
        }

        return player;
    }

    @Override
    public Player createPlayer(String tag) {
        logger.info("Creating player {}", tag);

        if(tag == null || notInClub(tag)) {
            logger.error("Error validating request: player tag must not be null or belong to a a non-club member");
            throw new IllegalArgumentException();
        }

        Player player;

        try {
            JPAPlayer jpaPlayer = new JPAPlayer();
            jpaPlayer.setTag(tag);
            playerRepository.save(jpaPlayer);
            HttpResponse<String> response = client.getPlayer(tag);
            BSPlayer bsPlayer = objectMapper.readValue(response.body(), BSPlayer.class);
            player = modelMapper.map(bsPlayer, Player.class);
        } catch (BsNotFoundException e) {
            logger.error("There was an error while creating the player {}. Player not found", tag);
            throw e;
        } catch (BsForbiddenException e) {
            logger.error("There was an error while creating the player {}. Access to BS API Forbidden. Check API key", tag);
            throw e;
        } catch (InterruptedException e) {
            logger.error("There was an error while creating the player {}. Generic error occured. Check stack trace", tag);
            Thread.currentThread().interrupt();
            throw new BusinessException();
        } catch (Exception e) {
            logger.error("There was an error while creating the player {}. Generic error occured. Check stack trace", tag);
            throw new BusinessException();
        }

        return player;
    }
    
    //#region Private methods

    private boolean notInClub(String tag) {
        boolean inClub = false;
        String[] clubIds = ClubConstants.getClubIds();

        int i = 0;
        while (i < clubIds.length && !inClub) {
            try {
                BSClub club = objectMapper.readValue(client.getClub(clubIds[i]).body(), BSClub.class);
                inClub = playerInBSClub(tag, club);
            } catch (IOException e) {
                throw new BusinessException();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new BusinessException();
            }
            i++;
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

    private void mapMegapigRegistriesToPlayer(JPAPlayer jpaPlayer, Player player) {
        for (JPAMegapigRegistry jpaMegapigRegistry : jpaPlayer.getMegapigRegistries()) {
            player.getClubEventRegistries().add(modelMapper.map(jpaMegapigRegistry, MegapigRegistry.class));
        }
    }

    //#endregion
    
}
