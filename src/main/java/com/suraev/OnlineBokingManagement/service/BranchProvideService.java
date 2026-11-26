package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.DefaultService;

import java.util.List;

public interface BranchProvideService {

    List<DefaultService> getAllServices(Long branchId);
    DefaultService getServiceById(Long id);
    void deleteServiceById(Long id);
    DefaultService addService(Long branchId, DefaultService defaultService);
}
