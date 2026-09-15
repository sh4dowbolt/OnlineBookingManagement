package com.suraev.OnlineBokingManagement.controllers;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branch")
public class BranchController {

    private  BranchService branchService;

    public BranchController(@Autowired  BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/add")
    ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {
       return new ResponseEntity<>(branchService.saveBranch(branch), HttpStatus.CREATED);
    }
}
