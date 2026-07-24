package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Table(name = "branches")
@Getter
@Setter
public class BranchService {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brnch_id")
    private Long id;

    private String name;

    private String address;
    private String phoneNumber;
    private String description;


/*
    private List<Master> masters;
    private List<Review> reviews;
    private List<Photo> portfolio;
    private List<Service> services;
*/




}
