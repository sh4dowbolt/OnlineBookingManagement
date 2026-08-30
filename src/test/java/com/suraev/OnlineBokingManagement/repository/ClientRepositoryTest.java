package com.suraev.OnlineBokingManagement.repository;

import com.suraev.OnlineBokingManagement.config.TestConfig;
import com.suraev.OnlineBokingManagement.entities.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


class ClientRepositoryTest extends TestConfig {

    @Autowired
    private ClientRepository repository;

    @Test
    void saveClient() {

        //given
        Client newClient = Client.builder()
                .username("sh4dowbolt")
                .password("MegaSecretPassword")
                .phoneNumber("8800353555")
                .build();

        //when
        Client saved = repository.save(newClient);

        //expected
        Assertions.assertThat(saved.getUsername()).isEqualTo(newClient.getUsername());
        Assertions.assertThat(saved.getPassword()).isEqualTo(newClient.getPassword());
        Assertions.assertThat(saved.getPhoneNumber()).isEqualTo(newClient.getPhoneNumber());
        Assertions.assertThat(saved.getId()).isNotNull();


    }

}