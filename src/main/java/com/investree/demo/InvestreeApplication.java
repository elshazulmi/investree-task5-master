package com.investree.demo;

import com.investree.demo.controller.fileupload.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


//@OpenAPIDefinition
@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class InvestreeApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestreeApplication.class, args);
    }

}
