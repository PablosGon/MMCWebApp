package com.pablosgon.mortismaycry.webapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablosgon.mortismaycry.webapi.entities.models.ClubMember;
import com.pablosgon.mortismaycry.webapi.entities.models.Player;
import com.pablosgon.mortismaycry.webapi.entities.models.StarPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.StarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.StarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSClubMember;
import com.pablosgon.mortismaycry.webapi.entities.models.bs.BSPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.entities.models.jpa.JPAStarWeekPlayer;


@Configuration
public class MapperConfig {
    
    @Bean
    ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(BSClubMember.class, ClubMember.class)
            .addMappings(m -> m.map(src -> src.getIcon().getId(), ClubMember::setIconId));

        mapper.typeMap(BSPlayer.class, Player.class)
            .addMappings(m -> {
                m.map(src -> src.getClub().getName(), Player::setClubName);
                m.map(src -> src.getClub().getTag(), Player::setClubTag);
                m.map(src -> src.getIcon().getId(), Player::setIconId);
            });

        mapper.typeMap(JPAPlayer.class, Player.class)
            .addMappings(m -> m.map(JPAPlayer::getSeasonTrophyProgress, Player::setSeasonTrophyProgress));

        mapper.typeMap(JPAStarPlayer.class, StarPlayer.class)
            .addMappings(m -> {
                m.map(src -> src.getSeason().getId(), StarPlayer::setSeasonId);
                m.map(src -> src.getPlayer().getTag(), StarPlayer::setPlayerTag);
            });

        mapper.typeMap(JPAStarWeekPlayer.class, StarWeekPlayer.class)
            .addMappings(m -> {
                m.map(src -> src.getSeason().getId(), StarWeekPlayer::setSeasonId);
                m.map(src -> src.getPlayer().getTag(), StarWeekPlayer::setPlayerTag);
            });

        mapper.typeMap(JPAStarSeasonPlayer.class, StarSeasonPlayer.class)
            .addMappings(m -> {
                m.map(src -> src.getSeason().getId(), StarSeasonPlayer::setSeasonId);
                m.map(src -> src.getPlayer().getTag(), StarSeasonPlayer::setPlayerTag);
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
