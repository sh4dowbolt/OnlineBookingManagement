package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Client;
import com.suraev.OnlineBokingManagement.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        verify(clientRepository, times(1)).getClientById(anyLong());
    }

    @Test
    void getClientById_shouldThrowExceptionWhenIdIsNegative() {
        Long clientId = -1L;
        Assertions.assertThrows(IllegalAccessException.class, () -> clientService.getClient(clientId));
        verify(clientRepository, times(1)).getClientById(anyLong());

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

