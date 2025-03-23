package com.pablosgon.mortismaycry.webapi.entities.requests;

public class UpdateMembersRequest {
    
    private int season;
    private int week;


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
