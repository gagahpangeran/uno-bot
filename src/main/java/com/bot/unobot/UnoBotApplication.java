package com.bot.unobot;

import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@LineMessageHandler
public class UnoBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnoBotApplication.class, args);
    }

}
