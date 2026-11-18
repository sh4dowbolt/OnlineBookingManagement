package com.suraev.OnlineBokingManagement.controllers;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.BranchService;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.service.BranchProvideService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static java.util.List.of;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;
import static org.testcontainers.shaded.org.hamcrest.Matchers.hasSize;

@WebMvcTest(BranchProvideServiceController.class)
class BranchProvideServiceControllerIT {
    @Autowired
    private  MockMvc mockMvc;
    @MockitoBean
    private  BranchProvideService branchProvideService ;
    @Autowired
    private  ObjectMapper objectMapper;


    private static final String GET_SERVICES_BY_BRANCH = "/branch/branchServices/{branchId}/all";
    private static final String CREATE_DEFAULT_SERVICE = "/branch/branchServices/{branchId}/create";

    @Nested
    class getAllServicesByBranch{

        @Test
        void getAllServicesWithStatusIsOk_200() throws Exception {

            Long branchId = 1L;
            Branch branch = new Branch();

            List<BranchService> branchServiceList = of(new BranchService(1L,"cleaning","full", branch),
                    new BranchService(2L,"cleaning","full", branch));

            //byte[] bodyToResponse = objectMapper.writeValueAsBytes(branchServiceList);

            when(branchProvideService.getAllServices(branchId)).thenReturn(branchServiceList);

            MvcResult mvcResult = mockMvc.perform(get(GET_SERVICES_BY_BRANCH, branchId))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(branchServiceList)))
                    .andReturn();

            //byte[] MvcByteResult = mvcResult.getResponse().getContentAsByteArray();

            verify(branchProvideService).getAllServices(eq(branchId));
            verifyNoMoreInteractions(branchProvideService);
            //org.assertj.core.api.Assertions.assertThat(MvcByteResult).isEqualTo(bodyToResponse);

        }

        @Test
        void getAllServicesWithStatusIsOk_200_butEmptyList() throws Exception {

            Long branchId = 1L;
            Branch branch = new Branch();

            List<BranchService> branchServiceList = Collections.emptyList();

            when(branchProvideService.getAllServices(branchId)).thenReturn(branchServiceList);


            mockMvc.perform(get(GET_SERVICES_BY_BRANCH, branchId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isEmpty());


            verify(branchProvideService).getAllServices(eq(branchId));
            verifyNoMoreInteractions(branchProvideService);
        }

        @Test
        void getAllServicesWithStatusNotFound_404() throws Exception {

            Long branchId = 1L;

            when(branchProvideService.getAllServices(branchId)).thenThrow(NotFoundException.class);

            mockMvc.perform(get(GET_SERVICES_BY_BRANCH, branchId))
                    .andExpect(status().isNotFound());

            verify(branchProvideService).getAllServices(eq(branchId));
            verifyNoMoreInteractions(branchProvideService);
        }
    }

    @Nested
    class addServiceToBranch{

        @Test
        void addServiceToBranch_returnStatusCreated() throws Exception {

            Long branchId = 1L;
            BranchService branchService = BranchService.builder().id(1L).description("testing").build();


            when(branchProvideService.addService(anyLong(), any(BranchService.class))).thenReturn(branchService);


            mockMvc.perform(
                    post(CREATE_DEFAULT_SERVICE, branchId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(branchService)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.description").value("testing"))
                    .andExpect(jsonPath("$.id").value(1L));

            verify(branchProvideService).addService(eq(branchId), argThat( x ->
                    "testing".equals(x.getDescription())
            ));

        }

        @Test
        void addServiceToBranch_shouldReturnNotFoundIfBranchDoesNotExist() throws Exception {
            Long branchId = 1L;
            BranchService branchService = BranchService.builder().id(1L).description("testing").build();


            when(branchProvideService.addService(anyLong(), any(BranchService.class))).thenThrow(NotFoundException.class);

            mockMvc.perform(
                            post(CREATE_DEFAULT_SERVICE, branchId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(branchService)))
                    .andExpect(status().isNotFound());

        }


    }



}