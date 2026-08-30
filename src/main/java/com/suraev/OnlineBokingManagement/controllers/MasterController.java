package com.suraev.OnlineBokingManagement.controllers;

import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.service.BranchMasterService;
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
    private final BranchMasterService branchMasterService;

    @PostMapping("/add")
    public ResponseEntity<Master> createMaster(@RequestBody Master master) {
        return ResponseEntity.ok(masterService.createMaster(master));
    }

    @PostMapping("/add/{branchId}/")
    public ResponseEntity<Master>createMasterRightInBranch(@RequestBody Master master, @PathVariable Long branchId) {
        Master masterFromDb = branchMasterService.addMasterRightToBranch(branchId, master);
        return ResponseEntity.ok(masterFromDb);
    }

    @PostMapping("/add/{branchId}/links/{masterId}")
    public ResponseEntity<Master> linkMasterToBranch(@PathVariable Long branchId, @PathVariable Long masterId) {
        Master masterFromDb = branchMasterService.linkMasterToBranch(branchId, masterId);
        return ResponseEntity.ok(masterFromDb);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaster(@PathVariable int id) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
