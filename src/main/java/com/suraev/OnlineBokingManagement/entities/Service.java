package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "serv_list")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "def")
    private String name;
    private String description;

}
