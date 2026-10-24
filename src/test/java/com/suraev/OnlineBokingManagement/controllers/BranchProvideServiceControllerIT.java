package com.suraev.OnlineBokingManagement.controllers;

import com.suraev.OnlineBokingManagement.entities.Branch;
import com.suraev.OnlineBokingManagement.entities.Service;
import com.suraev.OnlineBokingManagement.exception.NotFoundException;
import com.suraev.OnlineBokingManagement.service.BranchProvideService;
import com.suraev.OnlineBokingManagement.service.BranchProvideServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.org.checkerframework.common.value.qual.MatchesRegex;
import org.testcontainers.shaded.org.hamcrest.MatcherAssert;
import org.testcontainers.shaded.org.hamcrest.Matchers;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

import static java.util.List.of;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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


    private static final String SERVICE_MAIN = "/branch/services/{branchId}/all";


    @Test
    void getAllServicesWithStatusIsOk_200() throws Exception {

        Long branchId = 1L;
        Branch branch = new Branch();

        List<Service> serviceList = of(new Service(1L,"cleaning","full", branch),
                new Service(2L,"cleaning","full", branch));

        //byte[] bodyToResponse = objectMapper.writeValueAsBytes(serviceList);

        when(branchProvideService.getAllServices(branchId)).thenReturn(serviceList);

        MvcResult mvcResult = mockMvc.perform(get(SERVICE_MAIN, branchId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(serviceList)))
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

        List<Service> serviceList = Collections.emptyList();

        when(branchProvideService.getAllServices(branchId)).thenReturn(serviceList);


        mockMvc.perform(get(SERVICE_MAIN, branchId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());


        verify(branchProvideService).getAllServices(eq(branchId));
        verifyNoMoreInteractions(branchProvideService);
    }

    @Test
    void getAllServicesWithStatusNotFound_404() throws Exception {

        Long branchId = 1L;

        when(branchProvideService.getAllServices(branchId)).thenThrow(NotFoundException.class);

        mockMvc.perform(get(SERVICE_MAIN, branchId))
                .andExpect(status().isNotFound());

        verify(branchProvideService).getAllServices(eq(branchId));
        verifyNoMoreInteractions(branchProvideService);
    }



}