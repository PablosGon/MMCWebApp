package com.pablosgon.mortismaycry.webapi.entities.models.jpa;

import java.time.LocalDateTime;

import com.pablosgon.mortismaycry.webapi.enums.MegapigStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "megapigRegistries")
public class JPAMegapigRegistry {
    
    @Id
    private int id;
    
    @ManyToOne
    private JPAPlayer player;

    @Enumerated(EnumType.ORDINAL)
    private MegapigStatus status;

    private LocalDateTime dateTime;


    public JPAMegapigRegistry() { }

    public JPAMegapigRegistry(JPAPlayer player, MegapigStatus status) {
        this.player = player;
        this.status = status;
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

    public MegapigStatus getStatus() {
        return this.status;
    }

    public void setStatus(MegapigStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
