package com.suraev.OnlineBokingManagement.controllers;

import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/master")
public class MasterController {

    private final MasterService masterService;

    @PostMapping("/add")
    public ResponseEntity<Master> createMaster(@RequestBody Master master) {
        return ResponseEntity.ok(masterService.createMaster(master));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaster(@PathVariable int id) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
