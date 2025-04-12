package com.pablosgon.mortismaycry.webapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablosgon.mortismaycry.webapi.entities.models.StarPlayerRegistry;

public interface StarPlayerRegistryRepository extends JpaRepository<StarPlayerRegistry, Integer> {
    
}
