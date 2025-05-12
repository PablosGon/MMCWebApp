package com.pablosgon.mortismaycry.webapi.business;

import com.pablosgon.mortismaycry.webapi.entities.models.Club;
import com.pablosgon.mortismaycry.webapi.entities.requests.NewMegapigRequest;
import com.pablosgon.mortismaycry.webapi.entities.requests.UpdateMembersRequest;

public interface ClubBusiness {
    
    public Club getClub(String tag);
    public void updateMembers(UpdateMembersRequest request);
    public void newMegapigReport(NewMegapigRequest request);
}
