package com.alloymobile.audit.persistence.repository;

import com.alloymobile.audit.persistence.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String>{
    public Mono<Client> findClientByEmail(String email);
}
