package com.pablosgon.mortismaycry.webapi.models.entities.jpa;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("weekly")
@Table(name = "star_week_players")
public class JPAStarWeekPlayer extends JPAStarPlayer {
    
    private int week;
    private int trophies;

    public JPAStarWeekPlayer() {
        super();
    }

    public JPAStarWeekPlayer(JPAPlayer player, JPASeason season, int week, int trophies, int clubId) {
        super(player, season, clubId);
        this.week = week;
        this.trophies = trophies;
    }

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
