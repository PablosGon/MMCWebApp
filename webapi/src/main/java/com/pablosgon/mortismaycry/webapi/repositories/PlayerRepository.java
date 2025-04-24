package com.pablosgon.mortismaycry.webapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAPlayer;


public interface PlayerRepository extends JpaRepository<JPAPlayer, String> {
    
    @Query("select p from JPAPlayer p where p.tag = :tag")
    Optional<JPAPlayer> findPlayerByTag(String tag);

}
