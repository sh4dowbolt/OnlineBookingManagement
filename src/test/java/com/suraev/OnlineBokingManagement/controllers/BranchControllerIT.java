package com.suraev.OnlineBokingManagement.controllers;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.service.BranchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BranchController.class)
class BranchControllerIT {

    private final  MockMvc mockMvc;
    @MockitoBean
    private final BranchService branchServiceImpl;
    private final ObjectMapper mapper;

    private static final String BRANCH_URI= "/branch";
    private static final String BRANCH_URI_ADD= BRANCH_URI + "/add";

    public BranchControllerIT(@Autowired MockMvc mockMvc, @Autowired BranchService branchServiceImpl, @Autowired ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.branchServiceImpl = branchServiceImpl;
        this.mapper = mapper;
    }
    @Test
    void createBranch_withStatusIsCreated200() throws Exception {

        Branch branchToSave = createBranchToRequest();
        Branch savedBranch = createBranchForMock();

        when(branchServiceImpl.saveBranch(argThat(b -> "Mr.VIP".equals(b.getName())))).thenReturn(savedBranch);

        mockMvc.perform(MockMvcRequestBuilders.post(BRANCH_URI_ADD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(branchToSave)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mr.VIP"))
                .andExpect(jsonPath("$.id").value(1L));


        verify(branchServiceImpl).saveBranch(argThat(b -> "Mr.VIP".equals(b.getName())));


    }

    private static Branch createBranchForMock() {
        Branch savedBranch = Branch.builder()
                .name("Mr.VIP")
                .id(1L)
                .build();
        return savedBranch;
    }

    private static Branch createBranchToRequest() {
        Branch branchToSave = Branch.builder()
                .name("Mr.VIP")
                .build();
        return branchToSave;
    }


}