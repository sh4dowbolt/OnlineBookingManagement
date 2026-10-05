package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Service;

import java.util.List;

public interface BranchProvideService {

    List<Service> getAllServices(Long branchId);
    Service getServiceById(Long id);
    void deleteServiceById(Long id);
    Service addService(Service service);
}
