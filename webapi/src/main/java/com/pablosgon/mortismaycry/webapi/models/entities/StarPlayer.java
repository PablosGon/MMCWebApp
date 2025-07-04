package com.pablosgon.mortismaycry.webapi.models.entities;

public class StarPlayer {

    private String playerTag;
    private int seasonId;
    private String name;
    private int profileIconId;
    private int clubId;
    
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

    public int getClubId() {
        return this.clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

}
