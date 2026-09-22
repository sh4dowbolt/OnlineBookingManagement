package com.suraev.OnlineBokingManagement.controllers;

import com.suraev.OnlineBokingManagement.entities.Master;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.service.BranchMasterService;
import com.suraev.OnlineBokingManagement.service.MasterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MasterController.class)
class MasterControllerIT {

    @MockitoBean
    private BranchMasterService branchMasterService;
    @MockitoBean
    private MasterService masterService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String MASTER_URI = "/master";
    private static final String MASTER_URI_ADD = MASTER_URI+ "/add";
    private static final String MASTER_URI_DELETE = MASTER_URI+"/delete";
    private static final String MASTER_URI_ADD_RIGHT_TO_BRANCH = MASTER_URI+"/add/";
    private static final String MASTER_LINK_TO_BRANCH= MASTER_URI+"/add/{branchId}/links/{masterId}";



    @Nested
    @DisplayName("method addMaster")
    class addMaster{

        @Test
        void addMaster_shouldSaveMasterAndReturnMasterWithID() throws Exception {


            Master master = createMasterWithoutId();
            Master masterSaved = createMasterWithId();


            when(masterService.createMaster(any(Master.class))).thenReturn(masterSaved);


            mockMvc.perform(post(MASTER_URI_ADD)
                    .content(objectMapper.writeValueAsString(master))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.name").value("Vitaly"));

            verify(masterService).createMaster(any(Master.class));


        }


    }

    @Nested
    @DisplayName("method addMasterRightToBranch")
    class AddMasterRightToBranch{

        @Test
        void addMasterRightToBranch_shouldSaveMasterAndReturnMasterWithID() throws Exception {

            Long branchId = 1L;
            Master master = createMasterWithoutId();
            Master masterSaved = createMasterWithId();

            when(branchMasterService.addMasterRightToBranch(eq(branchId), any(Master.class))).thenReturn(masterSaved);

            mockMvc.perform(post(MASTER_URI_ADD+"/"+"{id}",branchId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(master)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1L));

            verify(branchMasterService).addMasterRightToBranch(eq(branchId),
                    argThat(m-> m.getName().equals("Vitaly")));
        }

        @Test
        void addMasterRightToBranch_shouldThrowNotFoundExceptionWhenBranchDoesNotExists() throws Exception {

            Long branchId = 1L;
            Master master = createMasterWithoutId();

            when(branchMasterService.addMasterRightToBranch(eq(branchId), any(Master.class)))
                    .thenThrow(NotFoundException.class);

            mockMvc.perform(post(MASTER_URI_ADD_RIGHT_TO_BRANCH+"{branchId}", branchId)
                    .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(master)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.body.title").value("Not Found"))
            .andDo(print());

            verify(branchMasterService).addMasterRightToBranch(eq(branchId), any(Master.class));

        }
    }

    @Nested
    @DisplayName("method linkMasterToBranch")
    class LinkMasterToBranch{

        @Test
        void linkMasterToBranch_shouldSaveMasterAndReturnMasterWithID() throws Exception {

            Long branchId = 1L;
            Long masterId = 1L;

            Master masterWithId = createMasterWithId();

            when(branchMasterService.linkMasterToBranch(eq(branchId), eq(masterId))).thenReturn(masterWithId);

            mockMvc.perform(post(MASTER_LINK_TO_BRANCH,branchId, masterId))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.name").value("Vitaly"));

            verify(branchMasterService).linkMasterToBranch(eq(branchId), eq(masterId));
        }

        @Test
        void linkMasterToBranch_shouldThrowNotFoundExceptionWhenBranchDoesNotExists() throws Exception {
            Long branchId = 1L;
            Long masterId = 1L;

            when(branchMasterService.linkMasterToBranch(eq(branchId), eq(masterId))).thenThrow(new NotFoundException("Branch Not Found"));

            mockMvc.perform(post(MASTER_LINK_TO_BRANCH,branchId, masterId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.body.detail").value("Branch Not Found"));
            verify(branchMasterService).linkMasterToBranch(eq(branchId), eq(masterId));

        }

        @Test
        void linkMasterToBranch_shouldThrowNotFoundExceptionWhenMasterDoesNotExists() throws Exception {

            Long branchId = 1L;
            Long masterId = 1L;

            when(branchMasterService.linkMasterToBranch(eq(branchId), eq(masterId))).thenThrow(new NotFoundException("Master Not Found"));

            mockMvc.perform(post(MASTER_LINK_TO_BRANCH,branchId, masterId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.body.detail").value("Master Not Found"));

            verify(branchMasterService).linkMasterToBranch(eq(branchId), eq(masterId));

        }
    }

    
    @Nested
    @DisplayName("method deleteMaster")
    class deleteMaster{

        @Test
        void deleteMaster_shouldDeleteMasterWithId() throws Exception {

            Long masterId = 1L;

            mockMvc.perform(MockMvcRequestBuilders.delete(MASTER_URI_DELETE+"/{id}", masterId))
                    .andExpect(status().isNoContent())
            .andExpect(content().string(""));

            verify(masterService).deleteMaster(masterId);
        }

    }

    private static Master createMasterWithId() {
        return Master.builder().id(1L).name("Vitaly").build();
    }

    private static Master createMasterWithoutId() {
        return Master.builder().name("Vitaly").build();
    }


}