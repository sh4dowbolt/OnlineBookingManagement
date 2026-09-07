package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Client;
import com.suraev.OnlineBokingManagement.repository.ClientRepository;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest  {

    @Mock
    private  ClientRepository clientRepository;
    @InjectMocks
    private ClientServiceImpl clientService;


    @Test
    void saveClient_shouldSaveAndReturnClient() {
        //given
        Client clientForTest = getClientForTest();

        Client clientSaved = getClientSaved();

        //when
        when(clientRepository.save(any(Client.class))).thenReturn(clientSaved);

        Client result = clientService.addClient(clientForTest);

        //expected
        Assertions.assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.getUsername()).isEqualTo(clientSaved.getUsername()),
                () -> assertThat(result.getPassword()).isEqualTo(clientSaved.getPassword()),
                () -> assertThat(result.getId()).isEqualTo(clientSaved.getId()));
        verify(clientRepository, times(1)).save(any(Client.class));
    }
    @Test
    void getClientById_shouldReturnClientIfClientExists() {
        //expected
        Long clientId = 1L;
        Client client = getClientForTest();
        client.setId(clientId);


        when(clientRepository.getClientById(any(Long.class))).thenReturn(Optional.of(client));

        Optional<Client> optionalClient = clientService.getClient(clientId);

        Assertions.assertTrue(optionalClient.isPresent());
        assertThat(optionalClient.get().getId()).isEqualTo(clientId);
        assertThat(optionalClient.get().getUsername()).isEqualTo(client.getUsername());
        assertThat(optionalClient.get().getPassword()).isEqualTo(client.getPassword());
        assertThat(optionalClient.get().getId()).isEqualTo(client.getId());

        verify(clientRepository, times(1)).getClientById(anyLong());


    }

    @Test
    void getClientById_shouldReturnClientIfClientDoesNotExist() {
        Long clientId = 2L;

        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.empty());

        Optional<Client> optionalClient = clientService.getClient(clientId);

        assertThat(optionalClient).isNotPresent();

        verify(clientRepository, times(1)).getClientById(anyLong());

    }

    @Test
    void getClientById_shouldThrowExceptionWhenIdIsNull() {
        Long clientId =null;

        Assertions.assertThrows(IllegalAccessException.class, () -> clientService.getClient(clientId));
        verify(clientRepository, times(1)).getClientById(anyLong());

    }

    @Test
    void getClientById_shouldThrowExceptionWhenIdIsZero() {

        Long clientId = 0L;
        Assertions.assertThrows(IllegalAccessException.class, () -> clientService.getClient(clientId));
        verify(clientRepository, never()).getClientById(anyLong());
    }

    @Test
    void getClientById_shouldThrowExceptionWhenIdIsNegative() {
        Long clientId = -1L;
        Assertions.assertThrows(IllegalAccessException.class, () -> clientService.getClient(clientId));
        verify(clientRepository, never()).getClientById(anyLong());

    }

    @Test
    void getAllClient_shouldReturnAllClientsIfClientsExist() {
        List<Client> clients = List.of(getClientForTest(), getClientForTest());

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        Assertions.assertAll(
                ()-> assertThat(result).isNotNull(),
                ()->assertThat(result.size()).isEqualTo(2),
                ()->assertThat(result.get(0)).isEqualTo(getClientForTest()),
                ()->assertThat(result.get(1)).isEqualTo(getClientForTest()),
                ()-> verify(clientRepository, times(1)).findAll()
        );
    }
    @Test
    void getAllClient_shouldThrowExceptionWhenClientsDoNotExist() {
        List<Client> clients = new ArrayList<>();

        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        List<Client> result = clientService.getAllClients();

        Assertions.assertAll(
                () ->  assertThat(result.size()).isEqualTo(0),
                () ->  assertThat(result.isEmpty()),
                () -> verify(clientRepository, times(1)).findAll()
        );
    }


    @Test
    void deleteCLient_shouldDeleteClient() {
        Long clientId = 1L;

        clientService.deleteClient(clientId);

        verify(clientRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteClient_shouldThrowExceptionIfIdIsNull() {
        Long clientId = null;

        Assertions.assertThrows(IllegalAccessException.class, () -> clientService.deleteClient(clientId));
        verify(clientRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteClient_shouldThrowExceptionIfIdIsZero() {
        Long clientId = 0L;
        Assertions.assertThrows(IllegalAccessException.class, () -> clientService.deleteClient(clientId));
        verify(clientRepository, never()).deleteById(anyLong());
    }


    private static Client getClientSaved() {
        return Client.builder()
                .id(1L)
                .password("dynasty")
                .phoneNumber("88822")
                .build();
    }

    private Client getClientForTest() {
            return Client.builder()
                    .password("dynasty")
                    .phoneNumber("88822")
                    .build();
        }

}

