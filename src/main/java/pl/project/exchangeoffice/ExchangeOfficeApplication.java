package pl.project.exchangeoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExchangeOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeOfficeApplication.class, args);
    }

}
