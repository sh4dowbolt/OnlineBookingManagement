package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.Master;

public interface BranchMasterService {
    Master addMasterRightToBranch(Long branchId, Master master);
    Master linkMasterToBranch(Long branchId, Long masterId);
}
