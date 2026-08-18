package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "branches")
@Getter
@Setter
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brnch_id")
    private Long id;

    private String name;

    private String address;
    private String phoneNumber;
    private String description;

    @OneToMany
    private List<Master> masters;
    /*@OneToMany
    private List<Review> reviews;
    @OneToMany
    private List<Photo> portfolio;*/
    @OneToMany
    private List<Service> services;





}
