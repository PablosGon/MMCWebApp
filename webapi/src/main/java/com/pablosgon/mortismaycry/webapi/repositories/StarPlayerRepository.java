package com.pablosgon.mortismaycry.webapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarPlayer;

public interface StarPlayerRepository extends JpaRepository<JPAStarPlayer, Integer>{

    @Query("select s from JPAStarPlayer s where s.player.tag = :tag")
    public List<JPAStarPlayer> findByPlayerTag(String tag);

}
