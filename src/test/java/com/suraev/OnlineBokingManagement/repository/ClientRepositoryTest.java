package com.suraev.OnlineBokingManagement.repository;

import com.suraev.OnlineBokingManagement.entities.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ClientRepositoryTest extends BaseRepositoryTest {

    private ClientRepository repository;

    @Test
    void saveClient() {

        //given
        Client newClient = new Client();

        //when
        Client saved = repository.save(newClient);

        //expected

        assertNotNull(saved);

    }

}