package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.entities.MasterBranch;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.repository.BranchRepository;
import com.suraev.OnlineBokingManagement.repository.MasterBranchRepository;
import com.suraev.OnlineBokingManagement.repository.MasterRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BranchMasterServiceImplTest {

    @InjectMocks
    private  BranchMasterServiceImpl branchMasterService;
    @Mock
    private BranchRepository branchRepository;
    @Mock
    private MasterRepository masterRepository;
    @Mock
    private MasterBranchRepository masterBranchRepository;

    @Nested
    @DisplayName("method addMasterRightToBranch")
    class addMasterRightToBranch {

        @Test
        void addMasterRightToBranch_shouldAddNewMasterToExistingBranch() {
            //given
            Long branchId = 1L;

            Master masterToSave = createMasterToSave();

            Branch branch = createBranch(branchId);

            Master savedMaster = createMasterWithId(1L);

            //when
            when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));
            when(masterRepository.save(masterToSave)).thenReturn(savedMaster);
            when(branchRepository.save(branch)).thenReturn(branch);

            Master actualResult = branchMasterService.addMasterRightToBranch(branchId, masterToSave);

            //result
            assertAll(
                    () -> assertThat(actualResult.getId()).isEqualTo(1L),
                    () -> assertThat(branch.getMasters()).contains(savedMaster)
            );

            verify(branchRepository).findById(branchId);
            verify(masterRepository).save(masterToSave);
            verify(branchRepository).save(branch);

        }


        @Test
        void addMasterRightToBranch_shouldNotFoundExecptionWhenBranchNotFound() {
            //given
            Long branchId = 1L;

            when(branchRepository.findById(branchId)).thenReturn(Optional.empty());


            Assertions.assertThrows(NotFoundException.class,
                    () ->   branchMasterService.addMasterRightToBranch(branchId, createMasterToSave()));

            verify(branchRepository).findById(branchId);
            verify(masterRepository, never()).save(any(Master.class));
            verifyNoInteractions(masterRepository);

        }

    }

    @Nested
    @DisplayName("method linkMasterToBranch")
    class linkMasterToBranch {

        @Test
        void linkMasterToBranch_whenBothExists_shouldLinkAndSave() {
            Branch branch = createBranch(1L);
            Master masterWithId = createMasterWithId(1L);


            when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
            when(masterRepository.findById(1L)).thenReturn(Optional.of(masterWithId));

            when(masterBranchRepository.save(any(MasterBranch.class))).thenAnswer(x-> x.getArgument(0));



            MasterBranch actualResult = branchMasterService.linkMasterToBranch(1L, 1L);

            assertThat(branch.getMasters()).extracting(Master::getId).containsOnly(1L);

            ArgumentCaptor<MasterBranch> mbCaptor = ArgumentCaptor.forClass(MasterBranch.class);
            verify(masterBranchRepository).save(mbCaptor.capture());
            MasterBranch saved = mbCaptor.getValue();

            assertThat(saved.getBranchId().getId()).isEqualTo(1L);
            assertThat(saved.getMasterId().getId()).isEqualTo(1L);
            assertThat(saved.getIsActive()).isTrue();
            assertThat(saved.getIsPrimary()).isTrue();
            assertThat(saved.getEndDate()).isEqualTo(LocalDateTime.MAX);

            assertThat(actualResult).isSameAs(saved);

            verify(branchRepository).findById(1L);
            verify(masterRepository).findById(1L);

            verify(masterRepository, never()).save(any());
            verify(branchRepository).save(branch);

        }

        @Test
        void linkMasterToBranch_BranchDoesntExist_shouldThrowNotFoundException() {

            Long branchId = 1L;
            Long masterId = 1L;

            when(branchRepository.findById(branchId)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> branchMasterService.linkMasterToBranch(branchId, masterId));

            verify(branchRepository).findById(branchId);
            verify(branchRepository, never()).save(any(Branch.class));
            verifyNoInteractions(masterRepository);



        }

        @Test
        void linkMasterToBranch_MasterDoesntExist_shouldThrowNotFoundException() {

            Long branchId = 1L;
            Long masterId = 1L;

            Branch branch = createBranch(branchId);

            when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));
            when(masterRepository.findById(masterId)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> branchMasterService.linkMasterToBranch(branchId, masterId));

            verify(branchRepository).findById(branchId);
            verify(branchRepository, never()).save(any(Branch.class));
            verify(masterRepository).findById(masterId);
            verify(masterRepository, never()).save(any(Master.class));

        }

    }


    private static Master createMasterWithId(Long masterId) {
        return Master.builder()
                .id(masterId)
                .name("Valeria")
                .phoneNumber("123")
                .build();
    }

    private static Branch createBranch(Long branchId) {
        return Branch.builder()
                .id(branchId)
                .name("MR.VIP")
                .build();
    }

    private static Master createMasterToSave() {
        return Master.builder()
                .name("Valeria")
                .phoneNumber("123")
                .build();
    }



}