package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "branchService",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Master> masters;
    /*@OneToMany
    private List<Review> reviews;
    @OneToMany
    private List<Photo> portfolio;*/
    @OneToMany
    private List<Service> services;


    public boolean addMaster(Master master) {
        if (masters == null) {
            masters = new ArrayList<>();
            masters.add(master);
            return true;
        }
        else {
            masters.add(master);
            return true;
        }
    }




}
