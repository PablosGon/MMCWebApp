package com.pablosgon.mortismaycry.webapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pablosgon.mortismaycry.webapi.config.converters.PlayerIconToIntegerConverter;
import com.pablosgon.mortismaycry.webapi.models.ClubMember;
import com.pablosgon.mortismaycry.webapi.models.bs.BSClubMember;

@Configuration
public class MapperConfig {
    
    @Bean
    ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();

        mapper.addConverter(new PlayerIconToIntegerConverter());

        mapper.typeMap(BSClubMember.class, ClubMember.class)
            .addMappings(m -> {
                m.map(src -> src.getIcon(), ClubMember::setIconId);
            });

        return mapper;
    }
}
