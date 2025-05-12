package com.pablosgon.mortismaycry.webapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAMegapigRegistry;

public interface MegapigRegistryRepository extends JpaRepository<JPAMegapigRegistry, Integer>{
    
    @Query("select r from JPAMegapigRegistry r order by r.dateTime desc")
    List<JPAMegapigRegistry> findAllSorted();

}
