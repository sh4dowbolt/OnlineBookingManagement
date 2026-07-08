package com.suraev.OnlineBokingManagement.entities;

import org.springframework.data.annotation.Id;

import java.util.List;

public class BranchService {
    @Id
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String description;

    private List<Master> masters;
    private List<Review> reviews;
    private List<Photo> portfolio;
    private List<Service> services;




}
