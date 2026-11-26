package com.suraev.OnlineBokingManagement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Entity
@Table(name = "master_branch")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterBranch {

    public MasterBranch(Branch branchId, Master masterId, Boolean isActive, Boolean isPrimary, LocalDateTime startDate, LocalDateTime endDate) {
        this.masterId = masterId;
        this.branchId = branchId;
        this.isActive = isActive;
        this.isPrimary = isPrimary;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Master masterId;

    @ManyToOne
    private Branch branchId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "valid_date_from")
    private LocalDateTime startDate;

    @Column(name = "valid_date_to" )
    private LocalDateTime endDate;

    @Column(name = "navi_date")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}

