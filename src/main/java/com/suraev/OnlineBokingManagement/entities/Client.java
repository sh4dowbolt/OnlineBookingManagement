package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clnt_id")
    private Long id;
    private String username;
    private String password;
    private String phoneNumber;
}
