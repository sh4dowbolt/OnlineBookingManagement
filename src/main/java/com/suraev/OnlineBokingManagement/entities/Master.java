package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "masters")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branchService;
    //private List<Review> reviews;

}
