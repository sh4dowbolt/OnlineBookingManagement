package com.suraev.OnlineBokingManagement.controllers;

import com.suraev.OnlineBokingManagement.entities.Client;
import com.suraev.OnlineBokingManagement.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ClientController.class)
class ClientControllerIT {


    @MockitoBean
    private ClientService service;
    @Autowired
    private  MockMvc mockMvc;
    @Autowired
    private  ObjectMapper mapper;



    private final String CLIENT_URI = "/client";
    private final String CLIENT_ADD = "/add";

    @Test
    void createClient_shouldReturn200IfEverythingIsOk() throws Exception {

        //given
        Client client = getClient();

        Mockito.when(service.addClient(any(Client.class))).thenReturn(client);

        mockMvc.perform(MockMvcRequestBuilders.post(CLIENT_URI+CLIENT_ADD)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(client)))
                .andExpect(status().isCreated());

        verify(service, Mockito.times(1))
                .addClient(any(Client.class));

    }

    private static Client getClient() {
        return Client.builder()
                .id(1L)
                .password("password")
                .phoneNumber("phoneNumber")
                .username("username").build();
    }

}