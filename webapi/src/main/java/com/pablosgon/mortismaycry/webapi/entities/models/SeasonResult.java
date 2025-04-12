package com.pablosgon.mortismaycry.webapi.entities.models;

import java.util.List;

public class SeasonResult {
    
    private int id;
    private List<ClubSeasonResult> clubSeasonResults;


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ClubSeasonResult> getClubSeasonResults() {
        return this.clubSeasonResults;
    }

    public void setClubSeasonResults(List<ClubSeasonResult> clubSeasonResults) {
        this.clubSeasonResults = clubSeasonResults;
    }

}
