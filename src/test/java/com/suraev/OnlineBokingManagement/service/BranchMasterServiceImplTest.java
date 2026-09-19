package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.repository.BranchRepository;
import com.suraev.OnlineBokingManagement.repository.MasterRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

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

    @Nested
    @DisplayName("method addMasterRightToBranch")
    class addMasterRightToBranch {

        @Test
        void addMasterRightToBranch_shouldAddNewMasterToExistingBranch() {
            //given
            Long branchId = 1L;

            Master masterToSave = createMasterToSave();

            Branch branch = createBranch(branchId);

            Master savedMaster = createMasterWithId();

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
            Master masterWithId = createMasterWithId();


            when(branchRepository.findById(branch.getId())).thenReturn(Optional.of(branch));
            when(masterRepository.findById(masterWithId.getId())).thenReturn(Optional.of(masterWithId));
            when(branchRepository.save(branch)).thenReturn(branch);
            when(masterRepository.save(masterWithId)).thenReturn(masterWithId);

            Master actualResult = branchMasterService.linkMasterToBranch(branch.getId(), masterWithId.getId());

            assertAll(
                    () -> assertThat(actualResult.getId()).isEqualTo(1L),
                    () -> assertThat(branch.getMasters()).contains(masterWithId),
                    () -> assertThat(masterWithId.getBranchService().getId()).isEqualTo(1L)
            );
            verify(branchRepository).findById(1L);
            verify(masterRepository).findById(1L);
            verify(branchRepository).save(branch);
            verify(masterRepository).save(masterWithId);

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


    private static Master createMasterWithId() {
        return Master.builder()
                .id(1L)
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