package com.pablosgon.mortismaycry.webapi.entities.models;

import java.util.List;

public class Season {
    
    private int id;
    private List<StarWeekPlayer> starPlayers;
    private StarSeasonPlayer grandStarPlayer;
    private StarLegend starLegend;
    private StarMaster starMaster;


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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
