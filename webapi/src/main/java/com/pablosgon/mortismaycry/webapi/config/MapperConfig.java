package com.pablosgon.mortismaycry.webapi.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pablosgon.mortismaycry.webapi.enums.MegapigStatus;
import com.pablosgon.mortismaycry.webapi.models.entities.ClubMember;
import com.pablosgon.mortismaycry.webapi.models.entities.MegapigRegistry;
import com.pablosgon.mortismaycry.webapi.models.entities.Player;
import com.pablosgon.mortismaycry.webapi.models.entities.StarPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.StarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.StarWeekPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.bs.BSClubMember;
import com.pablosgon.mortismaycry.webapi.models.entities.bs.BSPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAMegapigRegistry;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarSeasonPlayer;
import com.pablosgon.mortismaycry.webapi.models.entities.jpa.JPAStarWeekPlayer;



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

        Converter<MegapigStatus, Integer> statusToOrdinal = ctx -> ctx.getSource().ordinal();
        mapper.typeMap(JPAMegapigRegistry.class, MegapigRegistry.class)
            .addMappings(m -> m.using(statusToOrdinal).map(src -> src.getStatus(), MegapigRegistry::setStatus));

        return mapper;
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        return objectMapper;
    }

}
