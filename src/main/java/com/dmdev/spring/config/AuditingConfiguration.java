package com.dmdev.spring.config;

import com.dmdev.spring.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
@EnableEnversRepositories(basePackageClasses = ApplicationRunner.class)
public class AuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        // in practice: SecurityContext.getCurrentUser.getId (or Email, etc)
        return () -> Optional.of("dmdev");
    }

}
