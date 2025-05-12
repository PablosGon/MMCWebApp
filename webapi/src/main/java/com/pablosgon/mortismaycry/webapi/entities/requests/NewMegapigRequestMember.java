package com.pablosgon.mortismaycry.webapi.entities.requests;

import com.pablosgon.mortismaycry.webapi.enums.MegapigStatus;

public class NewMegapigRequestMember {
    
    private String playerTag;
    private MegapigStatus status;


    public String getPlayerTag() {
        return this.playerTag;
    }

    public void setPlayerTag(String playerTag) {
        this.playerTag = playerTag;
    }

    public MegapigStatus getStatus() {
        return this.status;
    }

    public void setStatus(MegapigStatus status) {
        this.status = status;
    }

}
