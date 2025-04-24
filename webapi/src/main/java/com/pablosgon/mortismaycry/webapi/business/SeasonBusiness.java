package com.pablosgon.mortismaycry.webapi.business;

import java.util.List;

import com.pablosgon.mortismaycry.webapi.entities.models.Season;
import com.pablosgon.mortismaycry.webapi.entities.requests.CreateSeasonRequest;

public interface SeasonBusiness {
    
    public List<Season> getSeasons();
    public void createSeason(CreateSeasonRequest request);

}
