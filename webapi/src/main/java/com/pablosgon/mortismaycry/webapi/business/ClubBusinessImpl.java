package com.pablosgon.mortismaycry.webapi.business;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

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
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarLegend;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarMaster;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPATrophyRegistry;
import com.pablosgon.mortismaycry.webapi.entities.requests.UpdateMembersRequest;
import com.pablosgon.mortismaycry.webapi.repositories.PlayerRepository;
import com.pablosgon.mortismaycry.webapi.repositories.StarPlayerRepository;
import com.pablosgon.mortismaycry.webapi.repositories.TrophyRegistryRepository;

public class ClubBusinessImpl implements ClubBusiness {
    
    private BSClient client;
    private ModelMapper mapper;
    private ObjectMapper objectMapper;
    private TrophyRegistryRepository trophyRegistryRepository;
    private PlayerRepository playerRepository;
    private StarPlayerRepository starPlayerRepository;

    public ClubBusinessImpl(
        BSClient bsClient,
        ModelMapper mapper,
        ObjectMapper objectMapper,
        TrophyRegistryRepository trophyRegistryRepository,
        PlayerRepository playerRepository,
        StarPlayerRepository starPlayerRepository
    ) {
        this.client = bsClient;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.trophyRegistryRepository = trophyRegistryRepository;
        this.playerRepository = playerRepository;
        this.starPlayerRepository = starPlayerRepository;
    }

    @Override
    public Club getClub(String tag) {
        if (tag == null) {
            throw new IllegalArgumentException("club tag must not be null");
        }

        System.out.println("Getting Club " + tag);

        Club club;

        try {
            HttpResponse<String> response = client.getClub(tag);
            BSClub bsClub = objectMapper.readValue(response.body(), BSClub.class);
            club = mapper.map(bsClub, Club.class);
            mapRegistriesToMembers(club.getMembers());
            mapStarBadgesToMembers(club.getMembers());
            System.out.println("Get Club successful: " + response.body());
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException(e.getMessage());
        }
        return club;
    }

    @Override
    public void updateMembers(UpdateMembersRequest request) throws Exception {
        if (request == null || invalidUpdateMembersRequest(request) || weekAlreadyRegistered(request.getSeason(), request.getWeek())) {
            throw new IllegalArgumentException();
        }

        try {
            List<BSClubMember> bsClubMembers = allCurrentMembers();
            addAllUnregisteredPlayers(bsClubMembers);
            List<JPATrophyRegistry> newRegistries = trophyRegistriesFromMembers(bsClubMembers, request.getSeason(), request.getWeek());
            trophyRegistryRepository.saveAll(newRegistries);
        } catch (Exception e) {
            throw e;
        }
    }

    //#region Private methods

    private void mapRegistriesToMembers(List<ClubMember> members) {
        List<JPATrophyRegistry> trophyRegistries = trophyRegistryRepository.findAllSorted();
        for (ClubMember clubMember : members) {
            List<JPATrophyRegistry> memberRegistries = trophyRegistries.stream().filter(x -> x.getPlayer().getTag().equals(clubMember.getTag().replace("#", ""))).toList();
            setLastRegistry(clubMember, memberRegistries);
        }
    }

    private void setLastRegistry(ClubMember clubMember, List<JPATrophyRegistry> memberRegistries) {
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
            mapStarBadgesCount(clubMember, memberStarBadges);
        }
    }

    private void mapStarBadgesCount(ClubMember member, List<JPAStarPlayer> badges) {
        int weeklyBadges = 0;
        int grandBadges = 0;
        int legendBadges = 0;
        int masterBadges = 0;

        for (JPAStarPlayer jpaStarPlayer : badges) {
            if (jpaStarPlayer instanceof JPAStarWeekPlayer) {
                weeklyBadges++;
            } else if (jpaStarPlayer instanceof JPAStarSeasonPlayer) {
                grandBadges++;
            } else if (jpaStarPlayer instanceof JPAStarLegend) {
                legendBadges++;
            } else if (jpaStarPlayer instanceof JPAStarMaster) {
                masterBadges++;
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

    private void addAllUnregisteredPlayers(List<BSClubMember> currentMembers) throws Exception {
        List<String> registeredPlayerTags = playerRepository.findAll().stream().map(x -> x.getTag()).toList();
        List<BSClubMember> unregisteredMembers = currentMembers.stream().filter(x -> !registeredPlayerTags.contains(x.getTag().replace("#", ""))).toList();

        for (BSClubMember member : unregisteredMembers) {
            String tag = member.getTag().replace("#", "");
            System.out.println("Creating player: " + tag);
            JPAPlayer newPlayer = new JPAPlayer();
            newPlayer.setTag(tag);
            playerRepository.save(newPlayer);
            System.out.println("Player " + tag + " created succesfully!");
        }
    }

    private List<BSClubMember> allCurrentMembers() throws Exception {
        List<BSClubMember> members = new ArrayList<>();
        String[] clubTags = ClubConstants.CLUB_IDS;
        for (String tag : clubTags) {
            HttpResponse<String> response = client.getClub(tag);
            BSClub club = objectMapper.readValue(response.body(), BSClub.class);
            for (BSClubMember member : club.getMembers()) {
                members.add(member);
            }
        }

        return members;
    }

    private List<JPATrophyRegistry> trophyRegistriesFromMembers(List<BSClubMember> members, int season, int week) {
        List<JPATrophyRegistry> registries = new ArrayList<>();

        for (BSClubMember member : members) {
            JPATrophyRegistry newRegistry = new JPATrophyRegistry(member.getTrophies(), season, week, new JPAPlayer(member.getTag().replace("#", "")));
            registries.add(newRegistry);
        }

        return registries;
    }

    //#endregion

}
