package com.dmdev.spring.config;

import com.dmdev.spring.config.condition.JpaCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@Conditional(JpaCondition.class)
public class JpaConfiguration {

//    @Bean
//    @ConfigurationProperties(prefix = "db") // the annotation seeks application.properties (or .yaml/yml) to find props with prefix "db" and then create our custom new DatabaseProperties bean
//    public DatabaseProperties databaseProperties() {
//        return new DatabaseProperties();
//    }

    @PostConstruct
    public void init() {
        log.info("JpaConfiguration is enabled.");
    }
}
