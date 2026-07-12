package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "masters")
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    //private List<Photo> portfolio;
    //private List<RateNumber> totalRate;
    @OneToMany
    private List<Service> services;
    @OneToOne
    private BranchService branchService;
    //private List<Review> reviews;

}
