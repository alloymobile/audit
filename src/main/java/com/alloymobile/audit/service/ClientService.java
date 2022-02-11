package com.alloymobile.audit.service;

import com.alloymobile.audit.application.security.TokenProvider;
import com.alloymobile.audit.application.utils.PasswordGenerator;
import com.alloymobile.audit.persistence.model.Client;
import com.alloymobile.audit.persistence.model.SignInRequest;
import com.alloymobile.audit.persistence.model.SignInResponse;
import com.alloymobile.audit.persistence.repository.ClientRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final TokenProvider tokenProvider;

    private final PasswordGenerator passwordGenerator;

    public ClientService(ClientRepository clientRepository, TokenProvider tokenProvider, PasswordGenerator passwordGenerator) {
        this.clientRepository = clientRepository;
        this.tokenProvider = tokenProvider;
        this.passwordGenerator = passwordGenerator;
    }

    public Flux<Client> findAllClient(){
        return this.clientRepository.findAll();
    }

    public Mono<Client> findClientById(String id){
        return this.clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException()));
    }

    public Mono<Client> addClient(Client client){
        return this.clientRepository.findClientByEmail(client.getEmail())
                .switchIfEmpty(Mono.defer(()->{
                    client.setId(new ObjectId().toString());
                    client.setPassword(this.passwordGenerator.encode(client.getPassword()));
                    return this.clientRepository.save(client);
                })).onErrorMap((e)->new RuntimeException());
    }

    public Mono<Client> updateClient(String id, Client client){
        return this.clientRepository.findById(id).flatMap(f->{
            f.setEmail(client.getEmail());
            return this.clientRepository.save(f);
        });
    }

    public Mono<Void> deleteClient(String id){
        return this.clientRepository.deleteById(id);
    }

    public Mono<SignInResponse> clientLogin(SignInRequest authRequest){
        return this.clientRepository.findClientByEmail(authRequest.getEmail())
                .filter(client-> this.passwordGenerator.encode(authRequest.getPassword()).equals(client.getPassword()))
                .map(c-> new SignInResponse(c,this.tokenProvider.generateToken(c)));
    }
}
