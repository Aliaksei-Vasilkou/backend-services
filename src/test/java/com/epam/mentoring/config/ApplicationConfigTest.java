package com.epam.mentoring.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(profiles = "dev")
class ApplicationConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testRequiredDataSourceBeanCreated() {
        // given

        // when
        DataSource dataSource = applicationContext.getBean("devDataSource", DataSource.class);

        // then
        assertThat(dataSource).isNotNull();
        assertThat(ReflectionTestUtils.getField(dataSource, "username")).isEqualTo("root");
        assertThat(ReflectionTestUtils.getField(dataSource, "password")).isEqualTo("root");
        assertThat(ReflectionTestUtils.getField(dataSource, "driverClassName")).isEqualTo("com.mysql.cj.jdbc.Driver");
        assertThat(ReflectionTestUtils.getField(dataSource, "jdbcUrl")).isEqualTo("jdbc:mysql://localhost:3306/adv_backend");
    }
}
