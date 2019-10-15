package ru.k0r0tk0ff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by korotkov_a_a on 26.10.2018.
 */
@EnableWebMvc
@SpringBootApplication(scanBasePackages = "ru.k0r0tk0ff")
@EntityScan(basePackages = {"ru.k0r0tk0ff.entity"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
