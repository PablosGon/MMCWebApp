package com.pablosgon.mortismaycry.webapi.entities.models.jpa;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("weekly")
@Table(name = "star_player_weekly_registries")
public class JPAStarPlayerWeeklyRegistry extends JPAStarPlayerRegistry {
    
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
