package ch.fhnw.oop2.footballfx.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class FootballfxApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootballfxApplication.class, args);
    }
}
