package com.pablosgon.mortismaycry.webapi.business;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.Map.Entry;

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
import com.pablosgon.mortismaycry.webapi.models.entities.TrophyRegistry;
import com.pablosgon.mortismaycry.webapi.models.entities.bs.BSClub;
import com.pablosgon.mortismaycry.webapi.models.entities.bs.BSClubMember;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPASeason;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarLegend;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarMaster;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPATrophyRegistry;
import com.pablosgon.mortismaycry.webapi.models.requests.CreateSeasonRequest;
import com.pablosgon.mortismaycry.webapi.exceptions.BsForbiddenException;
import com.pablosgon.mortismaycry.webapi.exceptions.BsNotFoundException;
import com.pablosgon.mortismaycry.webapi.repositories.SeasonRepository;
import com.pablosgon.mortismaycry.webapi.repositories.StarPlayerRepository;
import com.pablosgon.mortismaycry.webapi.repositories.TrophyRegistryRepository;

public class SeasonBusinessImpl implements SeasonBusiness {

    private SeasonRepository seasonRepository;
    private StarPlayerRepository starPlayerRepository;
    private TrophyRegistryRepository trophyRegistryRepository;
    private ModelMapper modelMapper;
    private BSClient client;
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(SeasonBusinessImpl.class);

    public SeasonBusinessImpl(
        ModelMapper modelMapper,
        SeasonRepository seasonRepository,
        StarPlayerRepository starPlayerRepository,
        TrophyRegistryRepository trophyRegistryRepository,
        BSClient client,
        ObjectMapper objectMapper
    ) {
        this.modelMapper = modelMapper;
        this.seasonRepository = seasonRepository;
        this.starPlayerRepository = starPlayerRepository;
        this.trophyRegistryRepository = trophyRegistryRepository;
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
    public String createSeason() {

        String response;

        try {
            int latestSeason = getLatestSeason();
            response = postSeasonResults(latestSeason);
            JPASeason newSeason = new JPASeason(latestSeason + 1);
            seasonRepository.save(newSeason);
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

        return response;

    }
    
    //#region Private Methods

    private String postSeasonResults(int season) throws IOException, InterruptedException {

        StringJoiner sj = new StringJoiner("\n\n");

        List<JPATrophyRegistry> trophyRegistries = trophyRegistryRepository.findRegistriesBySeason(season);
        for (int i = 0; i < ClubConstants.getClubNames().length; i++) {
            HttpResponse<String> response = client.getClub(ClubConstants.getClubIds()[i]);
            BSClub club = objectMapper.readValue(response.body(), BSClub.class);

            Map<BSClubMember, Integer> seasonProgress = new LinkedHashMap<>();
            Map<BSClubMember, Integer> seasonHighscores = new LinkedHashMap<>();
            for (BSClubMember bsClubMember : club.getMembers()) {
                List<JPATrophyRegistry> memberRegistries = trophyRegistries.stream().filter(x -> x.getPlayer().getTag().equals(bsClubMember.getTag().replace("#", ""))).toList();
                seasonProgress.put(bsClubMember, memberRegistries.getFirst().getTrophies() - memberRegistries.getLast().getTrophies());
                seasonHighscores.put(bsClubMember, memberRegistries.getFirst().getTrophies());
            }

            Entry<BSClubMember, Integer> bestProgressMember = seasonProgress.entrySet().stream().max(Map.Entry.comparingByValue()).get();
            JPAPlayer bestProgressPlayer = trophyRegistries.stream().filter(x -> x.getPlayer().getTag().equals(bestProgressMember.getKey().getTag().replace("#", ""))).findFirst().get().getPlayer();
            JPAStarSeasonPlayer newStarSeasonPlayer = new JPAStarSeasonPlayer(bestProgressPlayer, new JPASeason(season), i, bestProgressMember.getValue());
            starPlayerRepository.save(newStarSeasonPlayer);

            Entry<BSClubMember, Integer> bestHighscoreMember = seasonHighscores.entrySet().stream().max(Map.Entry.comparingByValue()).get();
            JPAPlayer bestHighscorePlayer = trophyRegistries.stream().filter(x -> x.getPlayer().getTag().equals(bestHighscoreMember.getKey().getTag().replace("#", ""))).findFirst().get().getPlayer();
            JPAStarLegend newStarLegend = new JPAStarLegend(bestHighscorePlayer, new JPASeason(season), i, bestHighscoreMember.getValue());
            starPlayerRepository.save(newStarLegend);

            sj.add(String.format("RESULTADOS TEMPORADA %d - %s - Gran Estelar: %s (+%d trofeos) - Leyenda Estelar: %s (%d trofeos)",
                season, 
                ClubConstants.getClubNames()[i],
                bestProgressMember.getKey().getName(),
                bestProgressMember.getValue(),
                bestHighscoreMember.getKey().getName(),
                bestHighscoreMember.getValue()));
        }

        return sj.toString();
    }

    private int getLatestSeason() {
        return seasonRepository.findAllDescOrder().getFirst().getId();
    }

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
