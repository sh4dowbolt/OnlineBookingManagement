package com.suraev.OnlineBokingManagement.controllers;


import com.suraev.OnlineBokingManagement.entities.BranchService;
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
    public ResponseEntity<List<BranchService>> getAllServiceByBranchId(@PathVariable Long branchId) {

        List<BranchService> allBranchServices = branchProvideService.getAllServices(branchId);
        return new ResponseEntity<>(allBranchServices, HttpStatus.OK);

    }

    @PostMapping("/services/{branchId}/create")
    public ResponseEntity<BranchService> createService(@PathVariable Long branchId, @RequestBody BranchService branchService) {

        return new ResponseEntity<>(branchProvideService.addService(branchId, branchService),HttpStatus.CREATED);
    }

}
