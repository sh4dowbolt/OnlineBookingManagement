package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.repository.MasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;

    public MasterServiceImpl(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    @Override
    public Master createMaster(Master master) {
        return masterRepository.save(master);
    }

    @Override
    public void deleteMaster(Long id) {
        masterRepository.deleteById(id);
    }
}
