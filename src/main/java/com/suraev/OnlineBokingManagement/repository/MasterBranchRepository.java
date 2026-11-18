package com.suraev.OnlineBokingManagement.repository;

import com.suraev.OnlineBokingManagement.entities.MasterBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterBranchRepository extends JpaRepository<MasterBranch, Long> {

    MasterBranch save(MasterBranch masterBranch);

}
