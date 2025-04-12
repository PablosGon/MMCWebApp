package com.pablosgon.mortismaycry.webapi.entities.models;

import java.util.ArrayList;
import java.util.List;

public class ClubSeasonResult {

    private int clubId;
    private List<StarWeekPlayer> starPlayers = new ArrayList<>();
    private StarSeasonPlayer grandStarPlayer;
    private StarLegend starLegend;
    private StarMaster starMaster;

    public ClubSeasonResult() { }

    public ClubSeasonResult(int clubId) {
        this.clubId = clubId;
    }
    
    public int getClubId() {
        return this.clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public List<StarWeekPlayer> getStarPlayers() {
        return this.starPlayers;
    }

    public void setStarPlayers(List<StarWeekPlayer> starPlayers) {
        this.starPlayers = starPlayers;
    }

    public StarSeasonPlayer getGrandStarPlayer() {
        return this.grandStarPlayer;
    }

    public void setGrandStarPlayer(StarSeasonPlayer grandStarPlayer) {
        this.grandStarPlayer = grandStarPlayer;
    }

    public StarLegend getStarLegend() {
        return this.starLegend;
    }

    public void setStarLegend(StarLegend starLegend) {
        this.starLegend = starLegend;
    }

    public StarMaster getStarMaster() {
        return this.starMaster;
    }

    public void setStarMaster(StarMaster starMaster) {
        this.starMaster = starMaster;
    }
}
