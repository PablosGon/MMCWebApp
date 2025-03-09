package com.pablosgon.mortismaycry.webapi.models.bs;

public class BSClubMember {
    
    private BSPlayerIcon icon;
    private String tag;
    private String name;
    private int trophies;
    private String role;
    private String nameColor;

    public BSPlayerIcon getIcon() {
        return this.icon;
    }

    public void setIcon(BSPlayerIcon icon) {
        this.icon = icon;
    }

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

    public int getTrophies() {
        return this.trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNameColor() {
        return this.nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

}
