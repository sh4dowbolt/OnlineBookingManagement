package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.Service;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.repository.BranchRepository;
import org.assertj.core.api.AbstractThrowableAssert;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

            List<Service> serviceList = List.of(
                    Service.builder().id(1L).description("boosting").build(),
                    Service.builder().id(2L).description("deboosting").build());

            Branch branch = createBranch(branchId, serviceList);

            when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));

            List<Service> actualResult = branchProvideServiceImpl.getAllServices(1L);

            assertAll(
                    () -> assertThat(actualResult).hasSize(2),
                    () -> assertThat(actualResult)
                            .extracting(Service::getId,Service::getDescription)
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

    }



    private static Branch createBranch(Long branchId, List<Service> serviceList) {
        return Branch.builder().id(branchId).services(serviceList).build();
    }


}