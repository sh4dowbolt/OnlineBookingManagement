package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.entities.MasterBranch;

public interface BranchMasterService {
    Master addMasterRightToBranch(Long branchId, Master master);
    MasterBranch linkMasterToBranch(Long branchId, Long masterId);
}
