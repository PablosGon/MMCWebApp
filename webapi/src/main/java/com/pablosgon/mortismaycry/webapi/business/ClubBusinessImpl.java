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
import com.pablosgon.mortismaycry.webapi.entities.models.Club;
import com.pablosgon.mortismaycry.webapi.entities.models.ClubMember;
import com.pablosgon.mortismaycry.webapi.entities.models.StarBadgeCase;
import com.pablosgon.mortismaycry.webapi.entities.models.StarLegend;
import com.pablosgon.mortismaycry.webapi.entities.models.StarMaster;
import com.pablosgon.mortismaycry.webapi.entities.models.StarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.StarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSClub;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSClubMember;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPASeason;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarLegend;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarMaster;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPATrophyRegistry;
import com.pablosgon.mortismaycry.webapi.entities.requests.UpdateMembersRequest;
import com.pablosgon.mortismaycry.webapi.exceptions.BusinessException;
import com.pablosgon.mortismaycry.webapi.exceptions.BsForbiddenException;
import com.pablosgon.mortismaycry.webapi.exceptions.BsNotFoundException;
import com.pablosgon.mortismaycry.webapi.repositories.PlayerRepository;
import com.pablosgon.mortismaycry.webapi.repositories.SeasonRepository;
import com.pablosgon.mortismaycry.webapi.repositories.StarPlayerRepository;
import com.pablosgon.mortismaycry.webapi.repositories.TrophyRegistryRepository;

public class ClubBusinessImpl implements ClubBusiness {
    
    private BSClient client;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private TrophyRegistryRepository trophyRegistryRepository;
    private PlayerRepository playerRepository;
    private StarPlayerRepository starPlayerRepository;
    private SeasonRepository seasonRepository;

    private Logger logger = LoggerFactory.getLogger(ClubBusinessImpl.class);

    public ClubBusinessImpl(
        BSClient bsClient,
        ModelMapper modelMapper,
        ObjectMapper objectMapper,
        TrophyRegistryRepository trophyRegistryRepository,
        PlayerRepository playerRepository,
        StarPlayerRepository starPlayerRepository,
        SeasonRepository seasonRepository
    ) {
        this.client = bsClient;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.trophyRegistryRepository = trophyRegistryRepository;
        this.playerRepository = playerRepository;
        this.starPlayerRepository = starPlayerRepository;
        this.seasonRepository = seasonRepository;
    }

    @Override
    public Club getClub(String tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Club tag must not be null");
        }

        logger.info("Getting Club {}", tag);

        Club club;

        try {
            HttpResponse<String> response = client.getClub(tag);
            BSClub bsClub = objectMapper.readValue(response.body(), BSClub.class);
            club = modelMapper.map(bsClub, Club.class);
            mapLastRegistriesToMembers(club.getMembers());
            mapStarBadgesToMembers(club.getMembers());
            logger.info("Successfully retrieved club information with tag {}", tag);
        } catch (BsNotFoundException e) {
            logger.error("There was an error while getting the club {}. Club not found", tag);
            throw e;
        } catch (BsForbiddenException e) {
            logger.error("There was an error while getting the club {}. Access to BS API Forbidden. Check API key", tag);
            throw e;
        } catch (InterruptedException e) {
            logger.error("There was an error while getting the club {}. Task interrupted", tag);
            Thread.currentThread().interrupt();
            throw new BusinessException();
        } catch (Exception e) {
            logger.error("There was an error while getting the club {}. Generic error occured. Check stack trace", tag);
            throw new BusinessException();
        }
        
        return club;
    }

    @Override
    public void updateMembers(UpdateMembersRequest request) {
        logger.info("Updating all members");

        if (request == null || invalidUpdateMembersRequest(request) || weekAlreadyRegistered(request.getSeason(), request.getWeek())) {
            throw new IllegalArgumentException();
        }

        try {
            for (int i = 0; i < ClubConstants.getClubIds().length; i++) {
                List<ClubMember> members = allCurrentMembersFromClub(ClubConstants.getClubIds()[i]);
                addAllUnregisteredPlayers(members);
                List<JPATrophyRegistry> newRegistries = trophyRegistriesFromMembers(members, request.getSeason(), request.getWeek());
                trophyRegistryRepository.saveAll(newRegistries);
                createWeeklyStarPlayers(members, i, request.getSeason(), request.getWeek());
            }
            logger.info("Successfully updated all club members");
        } catch (BsNotFoundException e) {
            logger.error("There was an error while updating members. Club not found");
            throw e;
        } catch (BsForbiddenException e) {
            logger.error("There was an error while updating members. Access to BS API Forbidden. Check API key");
            throw e;
        } catch (InterruptedException e) {
            logger.error("There was an error while updating members. Task interrupted.");
            Thread.currentThread().interrupt();
            throw new BusinessException();
        } catch (Exception e) {
            logger.error("There was an error while updating members. Generic error occured. Check stack trace");
            throw new BusinessException();
        }
    }

    //#region Private methods

    private void createWeeklyStarPlayers(List<ClubMember> members, int clubIndex, int season, int week) {
        mapLastRegistriesToMembers(members);
        members.sort((m1, m2) -> m2.getLastRegistry() - m1.getLastRegistry());
        ClubMember clubMemberWithMostProgression = members.getFirst();

        JPAPlayer playerWithMostProgression = playerRepository.findPlayerByTag(clubMemberWithMostProgression.getTag().replace("#", "")).orElse(null);
        JPASeason jpaSeason = seasonRepository.findById(season).orElse(null);

        JPAStarWeekPlayer newStarPlayer = new JPAStarWeekPlayer(playerWithMostProgression, jpaSeason, week, clubMemberWithMostProgression.getLastRegistry(), clubIndex);
        starPlayerRepository.save(newStarPlayer);
    }

    private void mapLastRegistriesToMembers(List<ClubMember> members) {
        List<JPATrophyRegistry> trophyRegistries = trophyRegistryRepository.findAllSorted();
        for (ClubMember clubMember : members) {
            List<JPATrophyRegistry> memberRegistries = trophyRegistries.stream().filter(x -> x.getPlayer().getTag().equals(clubMember.getTag().replace("#", ""))).toList();
            mapLastRegistry(clubMember, memberRegistries);
        }
    }

    private void mapLastRegistry(ClubMember clubMember, List<JPATrophyRegistry> memberRegistries) {
        int lastRegistry = -1;

        if (!memberRegistries.isEmpty()) {
            int offset = memberRegistries.get(memberRegistries.size() - 1).getWeek() == 0 ? 2 : 1;
            if (memberRegistries.size() > offset) {
                lastRegistry = memberRegistries.get(memberRegistries.size() - offset).getTrophies() - memberRegistries.get(memberRegistries.size() - (offset + 1)).getTrophies();
            }    
        }
        clubMember.setLastRegistry(lastRegistry);
    }

    private void mapStarBadgesToMembers(List<ClubMember> members) {
        List<JPAStarPlayer> starPlayers = starPlayerRepository.findAll();
        for (ClubMember clubMember : members) {
            List<JPAStarPlayer> memberStarBadges = starPlayers.stream().filter(x -> x.getPlayer().getTag().equals(clubMember.getTag().replace("#", ""))).toList();
            mapStarBadges(clubMember, memberStarBadges);
        }
    }

    private void mapStarBadges(ClubMember member, List<JPAStarPlayer> badges) {
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

        member.setStarBadgeCase(new StarBadgeCase(weeklyBadges, grandBadges, legendBadges, masterBadges));
    }

    private boolean invalidUpdateMembersRequest(UpdateMembersRequest request) {
        return request.getSeason() < 0
            || request.getWeek() < 0
            || request.getWeek() > 5;
    }

    private boolean weekAlreadyRegistered(int season, int week) {
        List<JPATrophyRegistry> registries = trophyRegistryRepository.findRegistriedByWeek(season, week);
        return !registries.isEmpty();
    }

    private void addAllUnregisteredPlayers(List<ClubMember> currentMembers) {
        List<String> registeredPlayerTags = playerRepository.findAll().stream().map(x -> x.getTag()).toList();
        List<ClubMember> unregisteredMembers = currentMembers.stream().filter(x -> !registeredPlayerTags.contains(x.getTag().replace("#", ""))).toList();

        for (ClubMember member : unregisteredMembers) {
            String tag = member.getTag().replace("#", "");
            logger.info("Creating unregistered player {}", tag);
            JPAPlayer newPlayer = new JPAPlayer();
            newPlayer.setTag(tag);
            playerRepository.save(newPlayer);
            logger.info("Successfully created unregistered player with tag {}", tag);
        }
    }

    private List<ClubMember> allCurrentMembersFromClub(String tag) throws IOException, InterruptedException {
        List<ClubMember> members = new ArrayList<>();
        HttpResponse<String> response = client.getClub(tag);
        BSClub club = objectMapper.readValue(response.body(), BSClub.class);

        for (BSClubMember member : club.getMembers()) {
            members.add(modelMapper.map(member, ClubMember.class));
        }

        return members;
    }

    private List<JPATrophyRegistry> trophyRegistriesFromMembers(List<ClubMember> members, int season, int week) {
        List<JPATrophyRegistry> registries = new ArrayList<>();

        for (ClubMember member : members) {
            JPATrophyRegistry newRegistry = new JPATrophyRegistry(member.getTrophies(), season, week, new JPAPlayer(member.getTag().replace("#", "")));
            registries.add(newRegistry);
        }

        return registries;
    }

    //#endregion

}
