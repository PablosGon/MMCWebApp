package com.pablosgon.mortismaycry.webapi.models.entities.bs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BSPlayer {
    
    private BSPlayerClub club;
    private BSPlayerIcon icon;
    private String tag;
    private String name;
    private int trophies;
    private int highestTrophies;
    private List<BSBrawlerStat> brawlers;
    private String nameColor;


    public BSPlayerClub getClub() {
        return this.club;
    }

    public void setClub(BSPlayerClub club) {
        this.club = club;
    }

    public BSPlayerIcon getIcon() {
        return this.icon;
    }

    public void setIcon(BSPlayerIcon icon) {
        this.icon = icon;
    }

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

    public List<BSBrawlerStat> getBrawlers() {
        return this.brawlers;
    }

    public void setBrawlers(List<BSBrawlerStat> brawlers) {
        this.brawlers = brawlers;
    }

    public String getNameColor() {
        return this.nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

}
