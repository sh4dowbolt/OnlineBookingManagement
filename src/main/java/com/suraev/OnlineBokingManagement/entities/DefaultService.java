package com.suraev.OnlineBokingManagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;

@Entity
@Table(name = "branch_services")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DefaultService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "def")
    private String name;
    private String description;
    private Category category;
    private Duration duration;
    private BigDecimal price;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonIgnore
    private Branch branch;


}
