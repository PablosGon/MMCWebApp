package com.pablosgon.mortismaycry.webapi.entities.models.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trophy_registries")
public class JPATrophyRegistry {
    
    @Id
    private int id;

    private int trophies;
    private int season;
    private int week;

    @ManyToOne
    @JoinColumn(name = "player")
    private JPAPlayer player;


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public JPAPlayer getPlayer() {
        return this.player;
    }

    public void setPlayer(JPAPlayer player) {
        this.player = player;
    }

}
