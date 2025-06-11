package com.pablosgon.mortismaycry.webapi.models.requests;

import java.util.List;

public class CreateMegapigReportRequest {
    
    private List<MegapigMember> members;


    public List<MegapigMember> getMembers() {
        return this.members;
    }

    public void setMembers(List<MegapigMember> members) {
        this.members = members;
    }

}
