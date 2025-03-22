package com.pablosgon.mortismaycry.webapi.entities.models;

public class Brawler {
    
    private int id;
    private int rank;
    private int trophies;
    private int highestTrophies;
    private int power;
    private String name;


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public int getPower() {
        return this.power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
