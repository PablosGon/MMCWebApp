package com.pablosgon.mortismaycry.webapi.entities.models;

public class StarBadgeCase {
    
    private int starPlayerBadges;
    private int grandStarPlayerBadges;
    private int starLegendBadges;
    private int starMasterBadges;

    public StarBadgeCase() { }

    public StarBadgeCase(
        int starPlayerBadges,
        int grandStarPlayerBadges,
        int starLegendBadges,
        int starMasterBadges
    ) {
        this.starPlayerBadges = starPlayerBadges;
        this.grandStarPlayerBadges = grandStarPlayerBadges;
        this.starLegendBadges = starLegendBadges;
        this.starMasterBadges = starMasterBadges;
    }

    public int getStarPlayerBadges() {
        return this.starPlayerBadges;
    }

    public void setStarPlayerBadges(int starPlayerBadges) {
        this.starPlayerBadges = starPlayerBadges;
    }

    public int getGrandStarPlayerBadges() {
        return this.grandStarPlayerBadges;
    }

    public void setGrandStarPlayerBadges(int grandStarPlayerBadges) {
        this.grandStarPlayerBadges = grandStarPlayerBadges;
    }

    public int getStarLegendBadges() {
        return this.starLegendBadges;
    }

    public void setStarLegendBadges(int starLegendBadges) {
        this.starLegendBadges = starLegendBadges;
    }

    public int getStarMasterBadges() {
        return this.starMasterBadges;
    }

    public void setStarMasterBadges(int starMasterBadges) {
        this.starMasterBadges = starMasterBadges;
    }

}
