package com.pablosgon.mortismaycry.webapi.entities.models;

public class StarPlayerWeeklyRegistry extends StarPlayerRegistry {
    
    private int week;
    private int trophies;


    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getTrophies() {
        return this.trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

}
