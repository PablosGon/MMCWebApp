package com.pablosgon.mortismaycry.webapi.models.entities;

import java.time.LocalDateTime;

import com.pablosgon.mortismaycry.webapi.enums.MegapigStatus;

public class MegapigRegistry {
           
    private MegapigStatus status;

    private LocalDateTime dateTime;


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
