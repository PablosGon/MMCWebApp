package com.pablosgon.mortismaycry.webapi.business;

import java.util.List;

import com.pablosgon.mortismaycry.webapi.models.entities.Season;
import com.pablosgon.mortismaycry.webapi.models.requests.CreateSeasonRequest;

public interface SeasonBusiness {
    
    public List<Season> getSeasons();
    public void createSeason(CreateSeasonRequest request);

}
