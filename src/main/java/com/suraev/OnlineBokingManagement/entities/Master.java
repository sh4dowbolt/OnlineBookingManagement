package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
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
    private List<DefaultService> defaultServices;

    @Column(name = "create_date")
    private ZonedDateTime createDate;
    @Column(name = "update_date")
    private ZonedDateTime updateDate;
    //private List<Review> reviews;
}
