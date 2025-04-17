package com.pablosgon.mortismaycry.webapi.business;

import java.util.List;

import com.pablosgon.mortismaycry.webapi.entities.models.Season;

public interface SeasonBusiness {
    
    public List<Season> getSeasons() throws Exception;

}
