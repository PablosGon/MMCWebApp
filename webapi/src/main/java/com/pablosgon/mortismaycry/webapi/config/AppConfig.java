package com.pablosgon.mortismaycry.webapi.config;

import java.net.http.HttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pablosgon.mortismaycry.webapi.clients.BSClient;
import com.pablosgon.mortismaycry.webapi.clients.BSClientImpl;

@Configuration
public class AppConfig {

    @Bean
    HttpClient webClient(){
        return HttpClient.newHttpClient();
    }

    @Bean
    BSClient bsClient(HttpClient httpClient){
        return new BSClientImpl(httpClient);
    }

}
