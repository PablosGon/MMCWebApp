package com.pablosgon.mortismaycry.webapi.entities.models;

public abstract class StarPlayerRegistry {

    private String tag;
    private int season;
    private int club;


    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getSeason() {
        return this.season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getClub() {
        return this.club;
    }

    public void setClub(int club) {
        this.club = club;
    }

}
