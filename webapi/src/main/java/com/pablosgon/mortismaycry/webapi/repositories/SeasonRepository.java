package com.pablosgon.mortismaycry.webapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPASeason;

public interface SeasonRepository extends JpaRepository<JPASeason, Integer> {
    
    @Query("select s from JPASeason s order by s.id desc")
    List<JPASeason> findAllDescOrder();

}
