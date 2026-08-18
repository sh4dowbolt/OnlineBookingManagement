package com.suraev.OnlineBokingManagement.repository;

import com.suraev.OnlineBokingManagement.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchServiceRepository extends JpaRepository<Branch, Long> {
}
