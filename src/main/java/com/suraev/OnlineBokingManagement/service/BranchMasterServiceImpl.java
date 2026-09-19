package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.repository.BranchRepository;
import com.suraev.OnlineBokingManagement.repository.MasterRepository;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;

@Service
public class BranchMasterServiceImpl implements BranchMasterService {
    private final BranchRepository branchServiceRepository;
    private final MasterRepository masterRepository;

    public BranchMasterServiceImpl(BranchRepository branchServiceRepository, MasterRepository masterRepository) {
        this.branchServiceRepository = branchServiceRepository;
        this.masterRepository = masterRepository;
    }

    @Override
    public Master addMasterRightToBranch(Long branchId, Master master) {
        final var branch = branchServiceRepository.findById(branchId)
                .orElseThrow(() -> new NotFoundException("Branch with id " + branchId + " not found"));
        //TODO добавить обработку искючения
        Master masterFromDB = masterRepository.save(master);

        branch.addMaster(masterFromDB);

        branchServiceRepository.save(branch);

        return masterFromDB;
    }

    @Override
    public Master linkMasterToBranch(Long branchId, Long masterId) {
        Branch branch = branchServiceRepository.findById(branchId).orElseThrow(() -> new NotFoundException("Branch with id " + branchId + " not found"));

        Master master = masterRepository.findById(masterId).orElseThrow(() -> new NotFoundException("Master with id " + masterId + " not found"));
        master.setBranchService(branch);

        branch.addMaster(master);
        branchServiceRepository.save(branch);
        masterRepository.save(master);

        return master;
    }
}
