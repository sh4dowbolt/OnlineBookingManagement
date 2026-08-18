package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.repository.BranchServiceRepository;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchServiceRepository branchServiceRepository;

    public BranchServiceImpl(BranchServiceRepository branchServiceRepository) {
        this.branchServiceRepository = branchServiceRepository;
    }

    @Override
    public Branch saveBranch(Branch branch) {
        return branchServiceRepository.save(branch);
    }
}
