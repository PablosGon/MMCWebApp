package com.pablosgon.mortismaycry.webapi.models.entities;

import java.util.List;

public class ClubMember {
    
    private int iconId;
    private String tag;
    private String name;
    private int trophies;
    private String role;
    private String nameColor;
    private int lastRegistry;
    private StarBadgeCase starBadgeCase;
    private List<Integer> lastMegapigs;
    private int firstSeason;


    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
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

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNameColor() {
        return this.nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    public int getLastRegistry() {
        return this.lastRegistry;
    }

    public void setLastRegistry(int lastRegistry) {
        this.lastRegistry = lastRegistry;
    }

    public StarBadgeCase getStarBadgeCase() {
        return this.starBadgeCase;
    }

    public void setStarBadgeCase(StarBadgeCase starBadgeCase) {
        this.starBadgeCase = starBadgeCase;
    }

    public List<Integer> getLastMegapigs() {
        return this.lastMegapigs;
    }

    public void setLastMegapigs(List<Integer> lastMegapigs) {
        this.lastMegapigs = lastMegapigs;
    }

    public int getFirstSeason() {
        return this.firstSeason;
    }

    public void setFirstSeason(int firstSeason) {
        this.firstSeason = firstSeason;
    }

}
