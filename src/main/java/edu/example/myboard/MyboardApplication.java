package edu.example.myboard;

import edu.example.myboard.config.DatabaseConfig;
import edu.example.myboard.config.SecurityConfig;
import edu.example.myboard.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication

@Import({SecurityConfig.class, DatabaseConfig.class, WebConfig.class})

public class MyboardApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(MyboardApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MyboardApplication.class, args);
    }

    @GetMapping
    public String HelloWorld(){
        return "Hello world!";
    }

}
