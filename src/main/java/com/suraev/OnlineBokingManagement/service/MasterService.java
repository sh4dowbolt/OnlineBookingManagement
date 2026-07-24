package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Master;

public interface MasterService {

    Master createMaster(Master master);
    void deleteMaster(Long id);
}
