package com.pablosgon.mortismaycry.webapi.business;

import com.pablosgon.mortismaycry.webapi.entities.models.Player;

public interface PlayerBusiness {
    
    public Player getPlayer(String tag);
    public Player createPlayer(String tag);
}
