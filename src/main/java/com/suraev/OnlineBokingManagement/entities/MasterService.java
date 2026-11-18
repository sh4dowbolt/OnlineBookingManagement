package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Duration;

@Table(name = "master_services")
@Embeddable
public class MasterService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;
    //private Photo photo;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Duration duration;
    private BigDecimal price;


}
