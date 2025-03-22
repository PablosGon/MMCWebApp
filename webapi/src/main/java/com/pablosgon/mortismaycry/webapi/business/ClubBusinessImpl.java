package com.pablosgon.mortismaycry.webapi.business;

import java.net.http.HttpResponse;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.entities.models.Club;
import com.pablosgon.mortismaycry.webapi.entities.models.ClubMember;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSClub;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPATrophyRegistry;
import com.pablosgon.mortismaycry.webapi.repositories.TrophyRegistryRepository;

public class ClubBusinessImpl implements ClubBusiness {
    
    private BSClient client;
    private ModelMapper mapper;
    private ObjectMapper objectMapper;
    private TrophyRegistryRepository trophyRegistryRepository;

    public ClubBusinessImpl(
        BSClient bsClient,
        ModelMapper mapper,
        ObjectMapper objectMapper,
        TrophyRegistryRepository trophyRegistryRepository
    ) {
        this.client = bsClient;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.trophyRegistryRepository = trophyRegistryRepository;
    }

    @Override
    public Club getClub(String tag) {
        if(tag == null) {
            throw new IllegalArgumentException("club tag must not be null");
        }

        System.out.println("Getting Club " + tag);

        Club club;

        try {
            HttpResponse<String> response = client.getClub(tag);
            BSClub bsClub = objectMapper.readValue(response.body(), BSClub.class);
            club = mapper.map(bsClub, Club.class);
            mapRegistriesToMembers(club.getMembers());
            System.out.println("Get Club successful: " + response.body());
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException(e.getMessage());
        }
        return club;
    }

    //#region Private methods

    private void mapRegistriesToMembers(List<ClubMember> members){
        List<JPATrophyRegistry> trophyRegistries = trophyRegistryRepository.findAll();
        for (ClubMember clubMember : members) {
            List<JPATrophyRegistry> memberRegistries = trophyRegistries.stream().filter(x -> x.getPlayer().getTag().equals(clubMember.getTag().replace("#", ""))).toList();
            setLastRegistry(clubMember, memberRegistries);
        }
    }

    private void setLastRegistry(ClubMember clubMember, List<JPATrophyRegistry> memberRegistries) {
        int lastRegistry = -1;
        if(memberRegistries.size() > 1) {
            lastRegistry = memberRegistries.get(memberRegistries.size() - 1).getTrophies() - memberRegistries.get(memberRegistries.size() - 2).getTrophies();
        }
        clubMember.setLastRegistry(lastRegistry);    
    }

    //#endregion

}
