package org.buffer.agendaterapeutas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgendaTerapeutasApplication {

    public static final Logger logger = LoggerFactory.getLogger(AgendaTerapeutasApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(AgendaTerapeutasApplication.class, args);
        logger.info("THERAPIST SCHEDULER STARTED...");
    }

}
