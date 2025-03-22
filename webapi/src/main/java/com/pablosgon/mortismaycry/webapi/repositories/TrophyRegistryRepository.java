package com.pablosgon.mortismaycry.webapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPATrophyRegistry;

public interface TrophyRegistryRepository extends JpaRepository<JPATrophyRegistry, Integer>{

    @Query("select r from JPATrophyRegistry r where r.player.tag = :tag")
    List<JPATrophyRegistry> findRegistriesByPlayerTag(String tag);

}
