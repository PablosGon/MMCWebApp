package com.pablosgon.mortismaycry.webapi.models.entities.jpa;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("master")
@Table(name = "star_masters")
public class JPAStarMaster extends JPAStarPlayer{
    
    private int rankedPoints;


    public int getRankedPoints() {
        return this.rankedPoints;
    }

    public void setRankedPoints(int rankedPoints) {
        this.rankedPoints = rankedPoints;
    }
    
}
