package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "masters")
@Getter
@Setter
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
    @PrimaryKeyJoinColumn(name = "brnch_id")
    private BranchService branchService;
    //private List<Review> reviews;

}
