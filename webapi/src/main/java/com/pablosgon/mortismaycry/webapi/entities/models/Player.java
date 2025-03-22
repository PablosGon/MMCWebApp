package com.pablosgon.mortismaycry.webapi.entities.models;

import java.util.ArrayList;
import java.util.List;

public class Player {
    
    private String tag;
    private String name;
    private String clubName;
    private String clubTag;
    private int iconId;
    private int trophies;
    private int highestTrophies;
    private List<Brawler> brawlers;
    private String nameColor;

    private List<TrophyRegistry> trophyRegistries = new ArrayList<>();
    private List<Integer> seasonTrophyProgress = new ArrayList<>();

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClubName() {
        return this.clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubTag() {
        return this.clubTag;
    }

    public void setClubTag(String clubTag) {
        this.clubTag = clubTag;
    }

    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getTrophies() {
        return this.trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public int getHighestTrophies() {
        return this.highestTrophies;
    }

    public void setHighestTrophies(int highestTrophies) {
        this.highestTrophies = highestTrophies;
    }

    public List<Brawler> getBrawlers() {
        return this.brawlers;
    }

    public void setBrawlers(List<Brawler> brawlers) {
        this.brawlers = brawlers;
    }

    public String getNameColor() {
        return this.nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    public List<TrophyRegistry> getTrophyRegistries() {
        return this.trophyRegistries;
    }

    public void setTrophyRegistries(List<TrophyRegistry> trophyRegistries) {
        this.trophyRegistries = trophyRegistries;
    }

    public List<Integer> getSeasonTrophyProgress() {
        return this.seasonTrophyProgress;
    }

    public void setSeasonTrophyProgress(List<Integer> seasonTrophyProgress) {
        this.seasonTrophyProgress = seasonTrophyProgress;
    }

}
