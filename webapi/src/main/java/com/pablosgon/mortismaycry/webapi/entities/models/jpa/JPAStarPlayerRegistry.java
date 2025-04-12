package com.pablosgon.mortismaycry.webapi.entities.models.jpa;

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
@Table(name = "star_player_registries")
public abstract class JPAStarPlayerRegistry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "player")
    private JPAPlayer player;

    private int season;
    private int clubId;


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

    public int getSeason() {
        return this.season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getClubId() {
        return this.clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }    

}
