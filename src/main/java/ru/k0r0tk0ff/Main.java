package ru.k0r0tk0ff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by korotkov_a_a on 26.10.2018.
 */

@SpringBootApplication(scanBasePackages = "ru.k0r0tk0ff")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
