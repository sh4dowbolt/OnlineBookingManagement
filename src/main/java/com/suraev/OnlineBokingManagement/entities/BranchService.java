package com.suraev.OnlineBokingManagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "branch_services")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BranchService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "def")
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonIgnore
    private Branch branch;

}
