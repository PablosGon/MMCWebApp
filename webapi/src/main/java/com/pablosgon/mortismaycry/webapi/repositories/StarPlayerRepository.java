package com.pablosgon.mortismaycry.webapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarPlayer;

public interface StarPlayerRepository extends JpaRepository<JPAStarPlayer, Integer>{

}
