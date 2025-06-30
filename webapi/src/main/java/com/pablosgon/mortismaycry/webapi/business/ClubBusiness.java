package com.pablosgon.mortismaycry.webapi.business;

import com.pablosgon.mortismaycry.webapi.models.entities.Club;
import com.pablosgon.mortismaycry.webapi.models.requests.CreateMegapigReportRequest;
import com.pablosgon.mortismaycry.webapi.models.requests.UpdateMembersRequest;

public interface ClubBusiness {
    
    public Club getClub(String tag, boolean isAdmin);
    public void updateMembers(UpdateMembersRequest request);
    public void createMegapigReport(CreateMegapigReportRequest request);
}
