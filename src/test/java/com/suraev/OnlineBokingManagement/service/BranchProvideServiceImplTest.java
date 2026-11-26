package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.DefaultService;
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

            List<DefaultService> defaultServiceList = List.of(
                    DefaultService.builder().id(1L).description("boosting").build(),
                    DefaultService.builder().id(2L).description("deboosting").build());

            Branch branch = createBranch(branchId, defaultServiceList);

            when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));

            List<DefaultService> actualResult = branchProvideServiceImpl.getAllServices(1L);

            assertAll(
                    () -> assertThat(actualResult).hasSize(2),
                    () -> assertThat(actualResult)
                            .extracting(DefaultService::getId, DefaultService::getDescription)
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

            List<DefaultService> actualResult = branchProvideServiceImpl.getAllServices(branchId);

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
            DefaultService defaultServiceToSave = createServiceWithoutId();


            Branch branchWithOutService = createBranch(branchId, null);


            when(branchRepository.findById(branchId)).thenReturn(Optional.of(branchWithOutService));
            when(branchRepository.save(any(Branch.class))).thenAnswer(
                    inv -> inv.getArgument(0));

            DefaultService actualResult = branchProvideServiceImpl.addService(branchId, defaultServiceToSave);

            assertAll(
                    () -> assertThat(actualResult.getId()).isEqualTo(1L),
                    () -> assertThat(actualResult.getDescription().equals(defaultServiceToSave.getDescription()))

            );

            verify(branchRepository).findById(branchId);
            verify(branchRepository).save(argThat(
                    x -> x.getDefaultServices().get(0).getDescription().equals(defaultServiceToSave.getDescription())
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

    private static DefaultService createServiceWithId(Branch branchWithOutService) {
        return DefaultService.builder().id(1L).description("boosting").branch(branchWithOutService).build();
    }

    private static DefaultService createServiceWithoutId() {
        return DefaultService.builder().description("boosting").build();
    }


    private static Branch createBranch(Long branchId, List<DefaultService> defaultServiceList) {
        return Branch.builder().id(branchId).defaultServices(defaultServiceList).build();
    }


}