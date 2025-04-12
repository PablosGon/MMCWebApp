package com.pablosgon.mortismaycry.webapi.entities.models;

import java.util.List;

public class StarSeason {
    
    private int season;
    private List<StarPlayerWeeklyRegistry> starPlayers;
    private StarPlayerSeasonalRegistry grandStarPlayer;
    private StarLegendRegistry starLegend;
    private StarMasterRegistry starMaster;


    public int getSeason() {
        return this.season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public List<StarPlayerWeeklyRegistry> getStarPlayers() {
        return this.starPlayers;
    }

    public void setStarPlayers(List<StarPlayerWeeklyRegistry> starPlayers) {
        this.starPlayers = starPlayers;
    }

    public StarPlayerSeasonalRegistry getGrandStarPlayer() {
        return this.grandStarPlayer;
    }

    public void setGrandStarPlayer(StarPlayerSeasonalRegistry grandStarPlayer) {
        this.grandStarPlayer = grandStarPlayer;
    }

    public StarLegendRegistry getStarLegend() {
        return this.starLegend;
    }

    public void setStarLegend(StarLegendRegistry starLegend) {
        this.starLegend = starLegend;
    }

    public StarMasterRegistry getStarMaster() {
        return this.starMaster;
    }

    public void setStarMaster(StarMasterRegistry starMaster) {
        this.starMaster = starMaster;
    }

}
