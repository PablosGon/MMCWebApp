package com.pablosgon.mortismaycry.webapi.entities.models.jpa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class JPAPlayer {
    
    @Id
    private String tag;

    @OneToMany(mappedBy = "player")
    private List<JPATrophyRegistry> trophyRegistries;

    @OneToMany(mappedBy = "player")
    private List<JPAStarPlayer> starPlayerRegistries;


    public JPAPlayer() {}

    public JPAPlayer(String tag) {
        this.tag = tag;
    }
    

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<JPATrophyRegistry> getTrophyRegistries() {
        return this.trophyRegistries;
    }

    public void setTrophyRegistries(List<JPATrophyRegistry> trophyRegistries) {
        this.trophyRegistries = trophyRegistries;
    }

    public List<JPAStarPlayer> getStarPlayerRegistries() {
        return this.starPlayerRegistries;
    }

    public void setStarPlayerRegistries(List<JPAStarPlayer> starPlayerRegistries) {
        this.starPlayerRegistries = starPlayerRegistries;
    }

    
    public List<Integer> getSeasonTrophyProgress() {
        if(trophyRegistries == null || trophyRegistries.isEmpty()) {
            return new ArrayList<>();
        }
        
        int currentSeason = Collections.max(trophyRegistries.stream().map(x -> x.getSeason()).toList());
        int currentWeek = Collections.max(trophyRegistries.stream().filter(x -> x.getSeason() == currentSeason).map(x -> x.getWeek()).toList());
        List<JPATrophyRegistry> seasonRegistries = trophyRegistries.stream().filter(x -> x.getSeason() == currentSeason).toList();

        List<Integer> filledSeasonTrophyList = new ArrayList<>();
        for(int i = 0; i <= currentWeek; i++) {
            int week = i;
            Optional<JPATrophyRegistry> weekRegistry = seasonRegistries.stream().filter(x -> x.getWeek() == week).findFirst();
            filledSeasonTrophyList.add(weekRegistry.isPresent() ? weekRegistry.get().getTrophies() : 0);
        }

        List<Integer> result = new ArrayList<>();
        for(int i = 1; i < filledSeasonTrophyList.size(); i++) {
            result.add(filledSeasonTrophyList.get(i - 1) > 0 ? filledSeasonTrophyList.get(i) - filledSeasonTrophyList.get(i-1) : 0);
        }

        return result;
    }
}
