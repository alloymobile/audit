package com.alloymobile.audit.resource;

import com.alloymobile.audit.application.config.SecurityConstants;
import com.alloymobile.audit.persistence.model.Client;
import com.alloymobile.audit.persistence.model.SignInRequest;
import com.alloymobile.audit.persistence.model.SignInResponse;
import com.alloymobile.audit.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@Tag(name = "Client", description = "This is a client api related to all operations to a client")
public class ClientResource {

    private final ClientService clientService;

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get all the clients", description = "It gets a list of all the clients in the system")
    @GetMapping(value= SecurityConstants.BASE_URL +"/clients", produces = "application/json")
    public Flux<Client> getAllClient(){
        return this.clientService.findAllClient();
    }

    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "404", description = "Client not found") })
    @GetMapping(value = SecurityConstants.BASE_URL +"/clients/{id}", produces = "application/json")
    public Mono<Client> getClientById(@PathVariable(name="id") String id){
        return this.clientService.findClientById(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = SecurityConstants.BASE_URL +"/clients/{id}")
    public Mono<Client> updateClient(@PathVariable(name="id") String id, @RequestBody Client client){
        return this.clientService.updateClient(id,client);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = SecurityConstants.BASE_URL +"/clients/{id}")
    public Mono<Void> deleteClient(@PathVariable(name="id") String id){
        return this.clientService.deleteClient(id);
    }

    //New user registration
    @PostMapping(value = SecurityConstants.FREE_URL+"/clients/signup", produces = "application/json")
    public Mono<Client> addClient(@Valid @RequestBody Client client){
        return this.clientService.addClient(client);
    }

    //Login user
    @PostMapping(value=SecurityConstants.FREE_URL+"/clients/signin", produces = "application/json")
    public Mono<SignInResponse> clientLogin(@RequestBody SignInRequest authRequest){
        return this.clientService.clientLogin(authRequest);
    }
}
