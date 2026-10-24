package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "branch_services")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "def")
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

}
