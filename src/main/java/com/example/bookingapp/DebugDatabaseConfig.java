package com.example.bookingapp;
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;

@Configuration
public class DebugDatabaseConfig {

    @Bean
    public CommandLineRunner schemaRunner(EntityManager em) {
        return args -> {
            var result = em.createNativeQuery("SHOW COLUMNS FROM booking").getResultList();

            System.out.println("C O L U M N S");

            for (Object row : result) {
                Object[] cols = (Object[]) row;
                System.out.println(Arrays.toString(cols));
            }
        };
    }
//    @Bean
//    public CommandLineRunner cleanSchema(JdbcTemplate jdbcTemplate) {
//        return args -> {
//            jdbcTemplate.execute("ALTER TABLE booking DROP COLUMN bedcount");
//            jdbcTemplate.execute("ALTER TABLE booking DROP COLUMN guest_count");
//            jdbcTemplate.execute("ALTER TABLE booking DROP COLUMN extrabed");
//            jdbcTemplate.execute("ALTER TABLE booking DROP COLUMN extra_bed");
//            jdbcTemplate.execute("ALTER TABLE booking DROP COLUMN start_date");
//            jdbcTemplate.execute("ALTER TABLE booking DROP COLUMN end_date");
//            jdbcTemplate.execute("ALTER TABLE booking DROP COLUMN submit_date");
//            jdbcTemplate.execute("ALTER TABLE booking DROP COLUMN nightcount");
//        };
//    }
}