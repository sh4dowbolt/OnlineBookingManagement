package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.repository.BranchServiceRepository;
import com.suraev.OnlineBokingManagement.repository.MasterRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class BranchMasterServiceImpl implements BranchMasterService {
    private final BranchServiceRepository branchServiceRepository;
    private final MasterRepository masterRepository;

    public BranchMasterServiceImpl(BranchServiceRepository branchServiceRepository, MasterRepository masterRepository) {
        this.branchServiceRepository = branchServiceRepository;
        this.masterRepository = masterRepository;
    }

    @Override
    public Master addMasterRightToBranch(Long branchId, Master master) {
        final var branch = branchServiceRepository.findById(branchId)
                .orElseThrow(NotFoundException::new);
        ///
        Master masterFromDB = masterRepository.save(master);

        branch.addMaster(masterFromDB);
        ///
        Branch branchFromDb = branchServiceRepository.save(branch);

        return masterFromDB;
    }

    @Override
    public Master linkMasterToBranch(Long branchId, Long masterId) {
        Branch branch = branchServiceRepository.findById(branchId).orElseThrow(NotFoundException::new);

        Master master = masterRepository.findById(masterId).orElseThrow(NotFoundException::new);
        master.setBranchService(branch);


        branch.addMaster(master);
        Branch branchFromDb = branchServiceRepository.save(branch);

        return master;
    }
}
