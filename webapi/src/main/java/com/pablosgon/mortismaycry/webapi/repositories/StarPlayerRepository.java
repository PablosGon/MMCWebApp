package com.pablosgon.mortismaycry.webapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarPlayer;

public interface StarPlayerRepository extends JpaRepository<JPAStarPlayer, Integer>{

    @Query("select s from JPAStarPlayer s where s.player.tag = :tag")
    List<JPAStarPlayer> findByPlayerTag(String tag);

    @Query("select s from JPAStarPlayer s join JPAStarWeekPlayer sw on s.id = sw.id where s.season.id = :season and sw.week = :week order by s.clubId")
    List<JPAStarPlayer> findStarWeekBySeasonAndWeek(int season, int week);

    @Query("select s from JPAStarPlayer s join JPAStarWeekPlayer sw on s.id = sw.id where s.season.id = :season and s.clubId = :club and sw.week = :week")
    Optional<JPAStarPlayer> findStarWeekBySeasonWeekAndClub(int season, int week, int club);
}
