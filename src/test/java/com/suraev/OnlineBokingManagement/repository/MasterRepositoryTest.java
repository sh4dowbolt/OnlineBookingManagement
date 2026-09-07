package com.suraev.OnlineBokingManagement.repository;

import com.suraev.OnlineBokingManagement.config.TestConfig;
import com.suraev.OnlineBokingManagement.entities.Master;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MasterRepositoryTest extends TestConfig {

    @Autowired
    private MasterRepository masterRepository;


    @Test
    void saveMaster() {

        Master testMaster = getTestMaster();

        Master savedMaster = masterRepository.save(testMaster);

        assertAll(
                ()-> assertThat(savedMaster.getName()).isEqualTo(testMaster.getName()),
                ()-> assertThat(savedMaster.getPhoneNumber()).isEqualTo(testMaster.getPhoneNumber()),
                ()-> assertThat(savedMaster.getId()).isNotNull()
        );
    }

    @Test
    void deleteMaster() {
        Master testMaster = getTestMaster();

        Master savedMaster = masterRepository.save(testMaster);

        masterRepository.delete(savedMaster);

        Optional<Master> optionalMaster = masterRepository.findById(savedMaster.getId());

        Assertions.assertFalse(optionalMaster.isPresent());
    }

    private static Master getTestMaster() {
        return Master.builder()
                .name("test")
                .phoneNumber("123456789")
                .build();
    }


}