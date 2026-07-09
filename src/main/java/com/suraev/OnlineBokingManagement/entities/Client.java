package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clnt_id")
    private Long id;
    private String username;
    private String password;
    private String phoneNumber;
}
