package com.pablosgon.mortismaycry.webapi.constants;

public class ClubConstants {
    private static final String[] CLUB_IDS = {
        "2jr9qvrgp",    // Mortis May Cry
        "2gy8yrp9r",    // Edgar May Cry
        "2p29vygu9"     // Fang May Cry
    };

    private ClubConstants(){}

    public static String[] getClubIds() {
        return CLUB_IDS.clone();
    }
}
