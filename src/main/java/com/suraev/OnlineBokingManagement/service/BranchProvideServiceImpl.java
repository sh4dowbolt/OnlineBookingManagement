package com.suraev.OnlineBokingManagement.service;


import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.DefaultService;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.repository.BranchRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchProvideServiceImpl implements BranchProvideService {

    private BranchRepository branchRepository;

    @Override
    public List<DefaultService> getAllServices(Long branchId) {

       return
               branchRepository.findById(branchId).map(Branch::getDefaultServices)
                       .orElseThrow(() -> new NotFoundException("Branch with id " + branchId + " does not exist"));

    }

    @Override
    public DefaultService getServiceById(Long id) {

        return null;
    }

    @Override
    public void deleteServiceById(Long id) {

    }

    @Override
    public DefaultService addService(Long branchId, DefaultService defaultService) {
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new NotFoundException("Branch with id " + branchId + " does not exist"));
        branch.addService(defaultService);

       return defaultService;

    }
}
