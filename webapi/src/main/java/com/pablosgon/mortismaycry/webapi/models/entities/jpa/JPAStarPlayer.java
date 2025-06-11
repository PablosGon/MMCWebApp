package com.pablosgon.mortismaycry.webapi.models.entities.jpa;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Table(name = "star_players")
public class JPAStarPlayer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "player")
    private JPAPlayer player;

    @ManyToOne
    @JoinColumn(name = "season")
    private JPASeason season;

    private int clubId;

    public JPAStarPlayer() {
        super();
    }

    public JPAStarPlayer(JPAPlayer player, JPASeason season, int clubId) {
        this.player = player;
        this.season = season;
        this.clubId = clubId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JPAPlayer getPlayer() {
        return this.player;
    }

    public void setPlayer(JPAPlayer player) {
        this.player = player;
    }

    public JPASeason getSeason() {
        return this.season;
    }

    public void setSeason(JPASeason season) {
        this.season = season;
    }

    public int getClubId() {
        return this.clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }    

}
