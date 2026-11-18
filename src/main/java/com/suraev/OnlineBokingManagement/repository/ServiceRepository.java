package com.suraev.OnlineBokingManagement.repository;

import com.suraev.OnlineBokingManagement.entities.BranchService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceRepository  extends JpaRepository<BranchService, Long> {
}
