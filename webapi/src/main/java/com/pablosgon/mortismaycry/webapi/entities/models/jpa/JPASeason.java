package com.pablosgon.mortismaycry.webapi.entities.models.jpa;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "seasons")
public class JPASeason {
    
    @Id
    private int id;

    @OneToMany(mappedBy = "season")
    private List<JPAStarPlayer> starPlayers;


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<JPAStarPlayer> getStarPlayers() {
        return starPlayers;
    }

    public void setStarPlayers(List<JPAStarPlayer> starPlayers) {
        this.starPlayers = starPlayers;
    }

}
