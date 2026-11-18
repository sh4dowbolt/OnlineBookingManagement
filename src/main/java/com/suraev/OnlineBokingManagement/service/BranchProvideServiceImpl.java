package com.suraev.OnlineBokingManagement.service;


import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.BranchService;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.repository.BranchRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchProvideServiceImpl implements BranchProvideService {

    private BranchRepository branchRepository;

    @Override
    public List<BranchService> getAllServices(Long branchId) {

       return
               branchRepository.findById(branchId).map(Branch::getBranchServices)
                       .orElseThrow(() -> new NotFoundException("Branch with id " + branchId + " does not exist"));

    }

    @Override
    public BranchService getServiceById(Long id) {

        return null;
    }

    @Override
    public void deleteServiceById(Long id) {

    }

    @Override
    public BranchService addService(Long branchId, BranchService branchService) {
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new NotFoundException("Branch with id " + branchId + " does not exist"));
        branch.addService(branchService);

       return branchService;

    }
}
