package com.pablosgon.mortismaycry.webapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.models.ClubMember;
import com.pablosgon.mortismaycry.webapi.models.Player;
import com.pablosgon.mortismaycry.webapi.models.bs.BSClubMember;
import com.pablosgon.mortismaycry.webapi.models.bs.BSPlayer;

@Configuration
public class MapperConfig {
    
    @Bean
    ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(BSClubMember.class, ClubMember.class)
            .addMappings(m -> {
                m.map(src -> src.getIcon().getId(), ClubMember::setIconId);
            });

        mapper.typeMap(BSPlayer.class, Player.class)
            .addMappings(m -> {
                m.map(src -> src.getClub().getName(), Player::setClubName);
                m.map(src -> src.getClub().getTag(), Player::setClubTag);
                m.map(src -> src.getIcon().getId(), Player::setIconId);
            });

        return mapper;
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        return objectMapper;
    }
}
