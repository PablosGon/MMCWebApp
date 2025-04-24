package com.pablosgon.mortismaycry.webapi.entities.models;

import com.pablosgon.mortismaycry.webapi.constants.ClubConstants;

public class Season {
    
    private int id;
    private ClubSeasonStarRegistry[] starPlayersByClub;

    public Season() {
        initializeClubSeasonStarRegistries();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClubSeasonStarRegistry[] getStarPlayersByClub() {
        return this.starPlayersByClub;
    }

    public void setStarPlayersByClub(ClubSeasonStarRegistry[] clubSeasonStarRegistries) {
        this.starPlayersByClub = clubSeasonStarRegistries;
    }

    //#region Private Methods

    private void initializeClubSeasonStarRegistries() {
        starPlayersByClub = new ClubSeasonStarRegistry[ClubConstants.getClubIds().length];
        for (int i = 0; i < starPlayersByClub.length; i++) {
            starPlayersByClub[i] = new ClubSeasonStarRegistry();
        }
    }

    //#endregion

}
