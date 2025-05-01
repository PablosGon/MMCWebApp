package com.pablosgon.mortismaycry.webapi.entities.models.jpa;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("season")
@Table(name = "star_season_players")
public class JPAStarSeasonPlayer extends JPAStarPlayer {
    
    private int trophies;


    public int getTrophies() {
        return this.trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

}
