package com.pablosgon.mortismaycry.webapi.entities.models;

import java.util.List;

public class StarBadgeCase {
    
    private List<StarWeekPlayer> starPlayerBadges;
    private List<StarSeasonPlayer> grandStarPlayerBadges;
    private List<StarLegend> starLegendBadges;
    private List<StarMaster> starMasterBadges;

    public StarBadgeCase() { }

    public StarBadgeCase(
        List<StarWeekPlayer> starPlayerBadges,
        List<StarSeasonPlayer> grandStarPlayerBadges,
        List<StarLegend> starLegendBadges,
        List<StarMaster> starMasterBadges
    ) {
        this.starPlayerBadges = starPlayerBadges;
        this.grandStarPlayerBadges = grandStarPlayerBadges;
        this.starLegendBadges = starLegendBadges;
        this.starMasterBadges = starMasterBadges;
    }

    public List<StarWeekPlayer> getStarPlayerBadges() {
        return this.starPlayerBadges;
    }

    public void setStarPlayerBadges(List<StarWeekPlayer> starPlayerBadges) {
        this.starPlayerBadges = starPlayerBadges;
    }

    public List<StarSeasonPlayer> getGrandStarPlayerBadges() {
        return this.grandStarPlayerBadges;
    }

    public void setGrandStarPlayerBadges(List<StarSeasonPlayer> grandStarPlayerBadges) {
        this.grandStarPlayerBadges = grandStarPlayerBadges;
    }

    public List<StarLegend> getStarLegendBadges() {
        return this.starLegendBadges;
    }

    public void setStarLegendBadges(List<StarLegend> starLegendBadges) {
        this.starLegendBadges = starLegendBadges;
    }

    public List<StarMaster> getStarMasterBadges() {
        return this.starMasterBadges;
    }

    public void setStarMasterBadges(List<StarMaster> starMasterBadges) {
        this.starMasterBadges = starMasterBadges;
    }

}
