package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.BranchService;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.repository.BranchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchProvideServiceImplTest {

    @Mock
    BranchRepository branchRepository;
    @InjectMocks
    BranchProvideServiceImpl branchProvideServiceImpl;

    @Nested
    @DisplayName("getAllBranches_method")
    class getAllBranches {

        @Test
        void getAllBranches_shouldReturnListOfBranches_whenBranchExists() {

            Long branchId = 1L;

            List<BranchService> branchServiceList = List.of(
                    BranchService.builder().id(1L).description("boosting").build(),
                    BranchService.builder().id(2L).description("deboosting").build());

            Branch branch = createBranch(branchId, branchServiceList);

            when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));

            List<BranchService> actualResult = branchProvideServiceImpl.getAllServices(1L);

            assertAll(
                    () -> assertThat(actualResult).hasSize(2),
                    () -> assertThat(actualResult)
                            .extracting(BranchService::getId, BranchService::getDescription)
                            .containsExactlyInAnyOrder(
                                    tuple(1L, "boosting"),
                                    tuple(2L, "deboosting")
                            )

            );

            verify(branchRepository).findById(1L);
        }


        @Test
        void getAllBranches_shouldThrowNotFoundException_whenBranchDoesNotExist() {
            Long branchId = 1L;

            when(branchRepository.findById(branchId)).thenReturn(Optional.empty());

            assertThatThrownBy( ()-> branchProvideServiceImpl.getAllServices(1L))
                            .isInstanceOf(NotFoundException.class)
                                    .hasMessage("Branch with id " + branchId + " does not exist");

            verify(branchRepository).findById(branchId);
            verifyNoMoreInteractions(branchRepository);

        }

        @Test
        void getAllBranches_shouldReturnEmptyList_whenBranchDoesNotContainService() {


            Long branchId = 1L;
            Branch branch = createBranch(branchId, Collections.emptyList());


            when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));

            List<BranchService> actualResult = branchProvideServiceImpl.getAllServices(branchId);

            assertThat(actualResult).isEmpty();


            verify(branchRepository).findById(1L);
            verifyNoMoreInteractions(branchRepository);

        }

    }

    @Nested
    @DisplayName("addService method")
    class addService {

        @Test
        void addService_shouldAddService_whenBranchExists() {

            Long branchId = 1L;
            BranchService branchServiceToSave = createServiceWithoutId();


            Branch branchWithOutService = createBranch(branchId, null);


            when(branchRepository.findById(branchId)).thenReturn(Optional.of(branchWithOutService));
            when(branchRepository.save(any(Branch.class))).thenAnswer(
                    inv -> inv.getArgument(0));

            BranchService actualResult = branchProvideServiceImpl.addService(branchId, branchServiceToSave);

            assertAll(
                    () -> assertThat(actualResult.getId()).isEqualTo(1L),
                    () -> assertThat(actualResult.getDescription().equals(branchServiceToSave.getDescription()))

            );

            verify(branchRepository).findById(branchId);
            verify(branchRepository).save(argThat(
                    x -> x.getBranchServices().get(0).getDescription().equals(branchServiceToSave.getDescription())
            ));

        }

        @Test
        void addService_shouldThrowNotFoundException_whenBranchDoesNotExist() {
            Long branchId = 1L;

            when(branchRepository.findById(branchId)).thenReturn(Optional.empty());


            assertThatThrownBy( ()-> branchProvideServiceImpl.addService(branchId, createServiceWithoutId()))
                    .isInstanceOf(NotFoundException.class);//.hasMessage("Branch with id " + branchId + " does not exist");
                                        // "Branch with id " + branchId + " does not exist"
            verify(branchRepository).findById(branchId);
            verifyNoMoreInteractions(branchRepository);


        }

    }

    private static BranchService createServiceWithId(Branch branchWithOutService) {
        return BranchService.builder().id(1L).description("boosting").branch(branchWithOutService).build();
    }

    private static BranchService createServiceWithoutId() {
        return BranchService.builder().description("boosting").build();
    }


    private static Branch createBranch(Long branchId, List<BranchService> branchServiceList) {
        return Branch.builder().id(branchId).branchServices(branchServiceList).build();
    }


}