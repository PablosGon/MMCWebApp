package com.pablosgon.mortismaycry.webapi.entities.requests;

import java.util.List;

public class NewMegapigRequest {
    
    private List<NewMegapigRequestMember> members;


    public List<NewMegapigRequestMember> getMembers() {
        return this.members;
    }

    public void setMembers(List<NewMegapigRequestMember> members) {
        this.members = members;
    }

}
