package com.suraev.OnlineBokingManagement.controllers;


import com.suraev.OnlineBokingManagement.entities.Service;
import com.suraev.OnlineBokingManagement.service.BranchProvideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchProvideServiceController {

    private BranchProvideService branchProvideService;

    public BranchProvideServiceController(BranchProvideService branchProvideService) {
        this.branchProvideService = branchProvideService;
    }



    @GetMapping("/services/{branchId}/all")
    public ResponseEntity<List<Service>> getAllServiceByBranchId(@PathVariable Long branchId) {

        List<Service> allServices = branchProvideService.getAllServices(branchId);
        return new ResponseEntity<>(allServices, HttpStatus.OK);

    }

}
