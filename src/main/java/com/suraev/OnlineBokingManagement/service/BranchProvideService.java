package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.BranchService;

import java.util.List;

public interface BranchProvideService {

    List<BranchService> getAllServices(Long branchId);
    BranchService getServiceById(Long id);
    void deleteServiceById(Long id);
    BranchService addService(Long branchId, BranchService branchService);
}
