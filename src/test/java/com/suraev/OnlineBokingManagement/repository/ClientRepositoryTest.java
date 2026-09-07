package com.suraev.OnlineBokingManagement.repository;

import com.suraev.OnlineBokingManagement.config.TestConfig;
import com.suraev.OnlineBokingManagement.entities.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class ClientRepositoryTest extends TestConfig {

    @Autowired
    private ClientRepository repository;

    @Test
    void saveClient() {

        //given
        Client newClient = getClient();

        //when
        Client saved = repository.save(newClient);

        //expected
        assertAll(
                () -> Assertions.assertThat(saved.getUsername()).isEqualTo(newClient.getUsername()),
                () -> Assertions.assertThat(saved.getPassword()).isEqualTo(newClient.getPassword()),
                () -> Assertions.assertThat(saved.getPhoneNumber()).isEqualTo(newClient.getPhoneNumber()),
                () -> Assertions.assertThat(saved.getId()).isNotNull()
        );
    }

    @Test
    void findClient() {
        Client client = getClient();

        Client saved = repository.save(client);

        Optional<Client> byId = repository.findById(saved.getId());

        assertAll(
                () -> Assertions.assertThat(byId).isNotNull(),
                () -> Assertions.assertThat(byId).isPresent()
        );

    }

    private static Client getClient() {
        Client newClient = Client.builder()
                .username("sh4dowbolt")
                .password("MegaSecretPassword")
                .phoneNumber("8800353555")
                .build();
        return newClient;
    }

}