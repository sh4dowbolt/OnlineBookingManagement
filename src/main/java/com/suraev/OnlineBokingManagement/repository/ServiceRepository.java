package com.suraev.OnlineBokingManagement.repository;

import com.suraev.OnlineBokingManagement.entities.DefaultService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceRepository  extends JpaRepository<DefaultService, Long> {
}
