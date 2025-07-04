package com.pablosgon.mortismaycry.webapi.business;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.constants.ClubConstants;
import com.pablosgon.mortismaycry.webapi.exceptions.BusinessException;
import com.pablosgon.mortismaycry.webapi.models.entities.ClubSeasonStarRegistry;
import com.pablosgon.mortismaycry.webapi.models.entities.Season;
import com.pablosgon.mortismaycry.webapi.models.entities.StarLegend;
import com.pablosgon.mortismaycry.webapi.models.entities.StarMaster;
import com.pablosgon.mortismaycry.webapi.models.entities.StarPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.StarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.StarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.bs.BSClub;
import com.pablosgon.mortismaycry.webapi.models.entities.bs.BSClubMember;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPASeason;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarLegend;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarMaster;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.models.requests.CreateSeasonRequest;
import com.pablosgon.mortismaycry.webapi.exceptions.BsForbiddenException;
import com.pablosgon.mortismaycry.webapi.exceptions.BsNotFoundException;
import com.pablosgon.mortismaycry.webapi.repositories.SeasonRepository;

public class SeasonBusinessImpl implements SeasonBusiness {

    private SeasonRepository seasonRepository;
    private ModelMapper modelMapper;
    private BSClient client;
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(SeasonBusinessImpl.class);

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
    public List<Season> getSeasons() {
        
        logger.info("Getting season history");

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

            logger.info("Successfully retireved seasons history");
        } catch (BsNotFoundException e) {
            logger.error("There was an error while retrieving seasons history. Club not found");
            throw e;
        } catch (BsForbiddenException e) {
            logger.error("There was an error while retrieving seasons history. Access to BS API Forbidden. Check API key");
            throw e;
        } catch (InterruptedException e) {
            logger.error("There was an error while retrieving seasons history. Task interrupted.");
            Thread.currentThread().interrupt();
            throw new BusinessException();
        } catch (Exception e) {
            logger.error("There was an error while retrieving seasons history. Generic error occured. Check stack trace");
            throw new BusinessException();
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

    private List<BSClubMember> allMembers() throws IOException, InterruptedException {
        List<BSClubMember> members = new ArrayList<>();
        String[] clubTags = ClubConstants.getClubIds();

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
