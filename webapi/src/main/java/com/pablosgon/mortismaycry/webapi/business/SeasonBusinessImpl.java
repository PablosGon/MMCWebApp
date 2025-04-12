package com.pablosgon.mortismaycry.webapi.business;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.pablosgon.mortismaycry.webapi.entities.models.Season;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPASeason;
import com.pablosgon.mortismaycry.webapi.repositories.SeasonRepository;

public class SeasonBusinessImpl implements SeasonBusiness {

    private SeasonRepository seasonRepository;
    private ModelMapper modelMapper;

    public SeasonBusinessImpl(ModelMapper modelMapper, SeasonRepository seasonRepository) {
        this.modelMapper = modelMapper;
        this.seasonRepository = seasonRepository;
    }

    @Override
    public List<Season> getSeasons() {
        
        System.out.println("Getting seasons");

        List<Season> seasons = new ArrayList<>();

        try {

            List<JPASeason> jpaSeasons = seasonRepository.findAll();
            for (JPASeason jpaSeason : jpaSeasons) {
                Season season = modelMapper.map(jpaSeason, Season.class);
                seasons.add(season);
            }

        } catch (Exception e) {
            System.err.println("Failed to get seasons: " + e.getMessage());
            throw e;
        }

        return seasons;
    }
    
    

}
