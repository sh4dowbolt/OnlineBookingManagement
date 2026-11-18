package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.entities.MasterBranch;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.repository.BranchRepository;
import com.suraev.OnlineBokingManagement.repository.MasterBranchRepository;
import com.suraev.OnlineBokingManagement.repository.MasterRepository;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;
import java.time.LocalDateTime;

@Service
public class BranchMasterServiceImpl implements BranchMasterService {
    private final BranchRepository branchServiceRepository;
    private final MasterRepository masterRepository;
    private final MasterBranchRepository masterBranchRepository;

    public BranchMasterServiceImpl(BranchRepository branchServiceRepository, MasterRepository masterRepository, MasterBranchRepository masterBranchRepository) {
        this.branchServiceRepository = branchServiceRepository;
        this.masterRepository = masterRepository;
        this.masterBranchRepository = masterBranchRepository;
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
    public MasterBranch linkMasterToBranch(Long branchId, Long masterId) {
        Branch branch = branchServiceRepository.findById(branchId).orElseThrow(() -> new NotFoundException("Branch with id " + branchId + " not found"));

        Master master = masterRepository.findById(masterId).orElseThrow(() -> new NotFoundException("Master with id " + masterId + " not found"));
        //master.setBranchService(branch);

        branch.addMaster(master);

        branchServiceRepository.save(branch);

        MasterBranch masterBranch = new MasterBranch(branch, master, true,true, LocalDateTime.now(), LocalDateTime.MAX);

        return masterBranchRepository.save(masterBranch);
    }
}
