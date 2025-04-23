package com.pablosgon.mortismaycry.webapi.business;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.constants.ClubConstants;
import com.pablosgon.mortismaycry.webapi.entities.models.ClubSeasonStarRegistry;
import com.pablosgon.mortismaycry.webapi.entities.models.Season;
import com.pablosgon.mortismaycry.webapi.entities.models.StarLegend;
import com.pablosgon.mortismaycry.webapi.entities.models.StarMaster;
import com.pablosgon.mortismaycry.webapi.entities.models.StarPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.StarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.StarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSClub;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSClubMember;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPASeason;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarLegend;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarMaster;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.entities.requests.CreateSeasonRequest;
import com.pablosgon.mortismaycry.webapi.repositories.SeasonRepository;

public class SeasonBusinessImpl implements SeasonBusiness {

    private SeasonRepository seasonRepository;
    private ModelMapper modelMapper;
    private BSClient client;
    private ObjectMapper objectMapper;

    public SeasonBusinessImpl(
        ModelMapper modelMapper,
        SeasonRepository seasonRepository,
        BSClient client,
        ObjectMapper objectMapper
    ) {
        this.modelMapper = modelMapper;
        this.seasonRepository = seasonRepository;
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Season> getSeasons() throws Exception {
        
        System.out.println("Getting seasons");

        List<Season> seasons = new ArrayList<>();

        try {
            List<JPASeason> jpaSeasons = seasonRepository.findAllDescOrder();
            List<BSClubMember> allMembers = allMembers();
            
            for (JPASeason jpaSeason : jpaSeasons) {
                Season season = modelMapper.map(jpaSeason, Season.class);
                mapStarPlayers(season, jpaSeason);
                mapNamesAndProfileIconsToAllStarPlayers(season, allMembers);
                seasons.add(season);
            }
        } catch (Exception e) {
            System.err.println("Failed to get seasons: " + e.getMessage());
            throw e;
        }

        return seasons;
    }

    @Override
    public void createSeason(CreateSeasonRequest request) {
        JPASeason newSeason = new JPASeason(request.getId());
        seasonRepository.save(newSeason);
    }
    
    //#region Private Methods

    private void mapStarPlayers(Season season, JPASeason jpaSeason) {
        for (JPAStarPlayer jpaStarPlayer : jpaSeason.getStarPlayers()) {
            if (jpaStarPlayer instanceof JPAStarWeekPlayer) {
                season.getStarPlayersByClub()[jpaStarPlayer.getClubId()].getWeeklyStarPlayers().add(modelMapper.map(jpaStarPlayer, StarWeekPlayer.class));
            } else if (jpaStarPlayer instanceof JPAStarSeasonPlayer) {
                season.getStarPlayersByClub()[jpaStarPlayer.getClubId()].setGrandStarPlayer(modelMapper.map(jpaStarPlayer, StarSeasonPlayer.class));
            } else if (jpaStarPlayer instanceof JPAStarLegend) {
                season.getStarPlayersByClub()[jpaStarPlayer.getClubId()].setStarLegend(modelMapper.map(jpaStarPlayer, StarLegend.class));
            } else if (jpaStarPlayer instanceof JPAStarMaster) {
                season.getStarPlayersByClub()[jpaStarPlayer.getClubId()].setStarMaster(modelMapper.map(jpaStarPlayer, StarMaster.class));
            }
        }

        for (ClubSeasonStarRegistry club : season.getStarPlayersByClub()) {
            club.getWeeklyStarPlayers().sort((o1, o2) -> o1.getWeek() - o2.getWeek());
        }
    }

    private void mapNamesAndProfileIconsToAllStarPlayers(Season season, List<BSClubMember> members) {
        for (ClubSeasonStarRegistry club : season.getStarPlayersByClub()) {
            for (StarPlayer starPlayer : club.getWeeklyStarPlayers()) {
                mapNameAndProfileIcon(starPlayer, members);
            }

            if (club.getGrandStarPlayer() != null) {
                mapNameAndProfileIcon(club.getGrandStarPlayer(), members);
            }

            if (club.getStarLegend() != null) {
                mapNameAndProfileIcon(club.getStarLegend(), members);
            }

            if (club.getStarMaster() != null) {
                mapNameAndProfileIcon(club.getStarMaster(), members);
            }
        }
    }

    private void mapNameAndProfileIcon(StarPlayer starPlayer, List<BSClubMember> members) {
        Optional<BSClubMember> member = members.stream().filter(x -> x.getTag().replace("#", "").equals(starPlayer.getPlayerTag())).findFirst();
        if (member.isPresent()) {
            starPlayer.setName(member.get().getName());
            starPlayer.setProfileIconId(member.get().getIcon().getId());
        } else {
            starPlayer.setName(starPlayer.getPlayerTag());
            starPlayer.setProfileIconId(-1);
        }
    }

    private List<BSClubMember> allMembers() throws Exception {
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

    //#endregion
}
