package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.repository.MasterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MasterServiceImplTest {

    @Mock
    private MasterRepository masterRepository;

    @InjectMocks
    private MasterServiceImpl masterServiceImpl;

    @Test
    void createMaster_shouldCreateMasterAndReturnClient() {

        Master master = getTestMasterWithOutId();
        Master masterWithId = getTestMasterWithId();

        //when
        when(masterRepository.save(any(Master.class))).thenReturn(masterWithId);

        Master result = masterServiceImpl.createMaster(master);

        assertAll(
                () -> verify(masterRepository).save(any(Master.class)),
                () -> assertThat(result.getId()).isNotNull(),
                () -> assertThat(result.getName()).isEqualTo(master.getName())
        );

    }

    @Test
    void deleteMaster_shouldDeleteMaster() {
        Long masterId = 1L;

        masterServiceImpl.deleteMaster(masterId);

        verify(masterRepository).deleteById(eq(masterId));

    }

    private Master getTestMasterWithId() {
        return Master.builder()
                .id(1L)
                .name("Vitaly")
                .phoneNumber("98222012")
                .build();
    }
    private Master getTestMasterWithOutId() {
        return Master.builder()
                .name("Vitaly")
                .phoneNumber("98222012")
                .build();
    }


}