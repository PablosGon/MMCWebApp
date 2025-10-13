package com.pablosgon.mortismaycry.webapi.models.entities.jpa;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("legend")
@Table(name = "star_legends")
public class JPAStarLegend extends JPAStarPlayer {
    
    private int trophies;

    public JPAStarLegend() {
        super();
    }

    public JPAStarLegend(JPAPlayer player, JPASeason season, int clubId, int trophies) {
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
