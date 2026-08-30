package com.suraev.OnlineBokingManagement.repository;

import com.suraev.OnlineBokingManagement.config.TestConfig;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class BaseRepositoryTest {
}
