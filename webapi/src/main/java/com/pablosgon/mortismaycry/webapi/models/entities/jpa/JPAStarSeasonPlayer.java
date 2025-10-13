package com.pablosgon.mortismaycry.webapi.models.entities.jpa;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("season")
@Table(name = "star_season_players")
public class JPAStarSeasonPlayer extends JPAStarPlayer {
    
    private int trophies;

    public JPAStarSeasonPlayer() {
        super();
    }

    public JPAStarSeasonPlayer(JPAPlayer player, JPASeason season, int clubId, int trophies) {
        super(player, season, clubId);
        this.trophies = trophies;
    }

    public int getTrophies() {
        return this.trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

}
