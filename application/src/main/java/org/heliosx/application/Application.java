package org.heliosx.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.heliosx")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
