package org.mayanze.dcims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MayanzeDcimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MayanzeDcimsApplication.class, args);
    }

}
