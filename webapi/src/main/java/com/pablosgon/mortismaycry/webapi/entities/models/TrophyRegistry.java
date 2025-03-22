package com.pablosgon.mortismaycry.webapi.entities.models;

public class TrophyRegistry {
    
    private int trophies;
    private int season;
    private int week;


    public int getTrophies() {
        return this.trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public int getSeason() {
        return this.season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

}
