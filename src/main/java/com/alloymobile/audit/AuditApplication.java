package com.alloymobile.audit;

import com.alloymobile.audit.application.config.EntityAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class AuditApplication {
    @Bean
    public ReactiveAuditorAware<String> auditorAware(){
        return new EntityAuditing();
    }
    public static void main(String[] args) {
        SpringApplication.run(AuditApplication.class, args);
    }
}
