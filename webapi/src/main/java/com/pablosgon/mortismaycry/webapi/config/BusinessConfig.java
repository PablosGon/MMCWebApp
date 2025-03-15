package com.pablosgon.mortismaycry.webapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.business.*;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;

@Configuration
public class BusinessConfig {
    
    @Bean
    ClubBusiness clubBusiness(BSClient client, ModelMapper mapper, ObjectMapper objectMapper) {
        return new ClubBusinessImpl(client, mapper, objectMapper);
    }

    @Bean
    PlayerBusiness playerBusiness(BSClient client, ModelMapper mapper, ObjectMapper objectMapper) {
        return new PlayerBusinessImpl(client, mapper, objectMapper);
    }

}
