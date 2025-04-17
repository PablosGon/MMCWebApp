package com.pablosgon.mortismaycry.webapi.entities.models;

import java.util.ArrayList;
import java.util.List;

public class ClubSeasonStarRegistry {
    
    private List<StarWeekPlayer> weeklyStarPlayers = new ArrayList<>();
    private StarSeasonPlayer grandStarPlayer;
    private StarLegend starLegend;
    private StarMaster starMaster;


    public List<StarWeekPlayer> getWeeklyStarPlayers() {
        return this.weeklyStarPlayers;
    }

    public void setWeeklyStarPlayers(List<StarWeekPlayer> weeklyStarPlayers) {
        this.weeklyStarPlayers = weeklyStarPlayers;
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
