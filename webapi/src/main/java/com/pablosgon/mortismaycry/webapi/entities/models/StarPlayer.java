package com.pablosgon.mortismaycry.webapi.entities.models;

public class StarPlayer {

    private String playerTag;
    private int seasonId;
    private String name;
    private int profileIconId;
    
    public String getPlayerTag() {
        return this.playerTag;
    }

    public void setPlayerTag(String player) {
        this.playerTag = player;
    }

    public int getSeasonId() {
        return this.seasonId;
    }

    public void setSeasonId(int season) {
        this.seasonId = season;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfileIconId() {
        return this.profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

}
