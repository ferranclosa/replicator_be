package com.closa.replicator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
@ComponentScan(basePackages = {"com.closa.replicator.*"})
public class ReplicatorApplication {
    public static void main(String[] args) throws IOException, SQLException {
        SpringApplication.run(ReplicatorApplication.class, args);
    }
}
