package com.pablosgon.mortismaycry.webapi.models.entities;

import java.util.List;

public class Club {
    
    private String tag;
    private String name;
    private String description;
    private int trophies;
    private int requiredTrophies;
    private List<ClubMember> members;
    private String type;
    private int badgeId;

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTrophies() {
        return this.trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public int getRequiredTrophies() {
        return this.requiredTrophies;
    }

    public void setRequiredTrophies(int requiredTrophies) {
        this.requiredTrophies = requiredTrophies;
    }

    public List<ClubMember> getMembers() {
        return members;
    }

    public void setMembers(List<ClubMember> members) {
        this.members = members;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBadgeId() {
        return this.badgeId;
    }

    public void setBadgeId(int badgeId) {
        this.badgeId = badgeId;
    }
    
}
