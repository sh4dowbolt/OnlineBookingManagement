package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "serv_list")
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

}
