package com.alloymobile.audit.application.config;

import org.springframework.data.domain.ReactiveAuditorAware;
import reactor.core.publisher.Mono;


public class EntityAuditing  implements ReactiveAuditorAware<String> {
    @Override
    public Mono<String> getCurrentAuditor() {
        return Mono.just("Tapas");
    }
}
