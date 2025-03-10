package com.pablosgon.mortismaycry.webapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pablosgon.mortismaycry.webapi.business.ClubBusiness;
import com.pablosgon.mortismaycry.webapi.clients.BSClient;

@Configuration
public class BusinessConfig {
    
    @Bean
    ClubBusiness clubBusiness(BSClient bsclient, ModelMapper mapper) {
        return new ClubBusiness(bsclient, mapper);
    }

}
