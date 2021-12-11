package com.test.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDB {

    private static final Logger log = LoggerFactory.getLogger(InitDB.class);

    @Bean
    CommandLineRunner initDatabase(TodoRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Todo("Do Stuff")));
            log.info("Preloading " + repository.save(new Todo("Do Other Stuff", false)));
        };
    }
}
