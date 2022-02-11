package com.alloymobile.audit.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SignInResponse extends Client implements Serializable {
    private String token;

    public SignInResponse(Client client, String token) {
        this.setId(client.getId());
        this.setEmail(client.getEmail());
        this.token = token;
    }
}
