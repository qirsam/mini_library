package com.qirsam.mini_library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MiniLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniLibraryApplication.class, args);
        System.out.println();
    }

}
