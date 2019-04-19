package com.craftsmanship.miniangular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println(System.getProperty("file.encoding"));
        SpringApplication.run(Application.class, args);
    }

}
