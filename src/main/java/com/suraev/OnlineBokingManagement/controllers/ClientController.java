package com.suraev.OnlineBokingManagement.controllers;

import com.suraev.OnlineBokingManagement.entities.Client;
import com.suraev.OnlineBokingManagement.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping(value = "/add")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        return new ResponseEntity<>(clientService.addClient(client), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/delete/{clnt_id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String clnt_id) {
        Long id = Long.parseLong(clnt_id);
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
