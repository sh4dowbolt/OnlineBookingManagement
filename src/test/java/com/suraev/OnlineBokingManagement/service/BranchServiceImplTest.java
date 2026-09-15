package com.suraev.OnlineBokingManagement.service;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.repository.BranchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {

    @InjectMocks
    private BranchServiceImpl branchService;
    @Mock
    private BranchRepository branchRepository;

    @Test
    void saveBranch_shouldSaveBranch() {
        Branch branch = Branch.builder()
                .name("Mr.Vip")
                .build();
        Branch branchSaved = Branch.builder()
                .id(1L)
                .name("Mr.Vip")
                .build();

        Mockito.when(branchRepository.save(branch)).thenReturn(branchSaved);

        Branch actualResult = branchService.saveBranch(branch);

        Assertions.assertAll(
                () -> assertThat(actualResult.getName()).isEqualTo(branchSaved.getName()),
                () -> assertThat(actualResult.getId()).isEqualTo(1L),
                () -> assertThat(actualResult).isSameAs(branchSaved)

        );
        verify(branchRepository).save(branch);
    }

}