package com.pablosgon.mortismaycry.webapi.business;

import com.pablosgon.mortismaycry.webapi.models.entities.Club;
import com.pablosgon.mortismaycry.webapi.models.requests.CreateMegapigReportRequest;

public interface ClubBusiness {
    
    public Club getClub(String tag, boolean isAdmin);
    public String updateMembers();
    public void createMegapigReport(CreateMegapigReportRequest request);
}
