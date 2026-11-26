package com.suraev.OnlineBokingManagement.controllers;


import com.suraev.OnlineBokingManagement.entities.DefaultService;
import com.suraev.OnlineBokingManagement.service.BranchProvideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchProvideServiceController {

    private BranchProvideService branchProvideService;

    public BranchProvideServiceController(BranchProvideService branchProvideService) {
        this.branchProvideService = branchProvideService;
    }



    @GetMapping("/services/{branchId}/all")
    public ResponseEntity<List<DefaultService>> getAllServiceByBranchId(@PathVariable Long branchId) {

        List<DefaultService> allDefaultServices = branchProvideService.getAllServices(branchId);
        return new ResponseEntity<>(allDefaultServices, HttpStatus.OK);

    }

    @PostMapping("/services/{branchId}/create")
    public ResponseEntity<DefaultService> createService(@PathVariable Long branchId, @RequestBody DefaultService defaultService) {

        return new ResponseEntity<>(branchProvideService.addService(branchId, defaultService),HttpStatus.CREATED);
    }

}
