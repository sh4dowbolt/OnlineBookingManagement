package com.suraev.OnlineBokingManagement.entities;

import java.util.List;

public class Master {
    private Long id;
    private String name;
    private String phoneNumber;
    private List<Photo> portfolio;
    private List<RateNumber> totalRate;
    private List<Service> services;
    private BranchService branchService;
    private List<Review> reviews;

}
