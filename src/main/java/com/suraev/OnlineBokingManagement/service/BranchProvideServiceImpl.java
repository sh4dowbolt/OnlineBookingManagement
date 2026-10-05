package com.suraev.OnlineBokingManagement.service;


import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.repository.BranchRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BranchProvideServiceImpl implements BranchProvideService {

    private BranchRepository branchRepository;

    @Override
    public List<com.suraev.OnlineBokingManagement.entities.Service> getAllServices(Long branchId) {

       return
               branchRepository.findById(branchId).map(Branch::getServices)
                       .orElseThrow(() -> new NotFoundException("Branch with id " + branchId + " does not exist"));

    }

    @Override
    public com.suraev.OnlineBokingManagement.entities.Service getServiceById(Long id) {
        return null;
    }

    @Override
    public void deleteServiceById(Long id) {

    }

    @Override
    public com.suraev.OnlineBokingManagement.entities.Service addService(com.suraev.OnlineBokingManagement.entities.Service service) {
        return null;
    }
}
