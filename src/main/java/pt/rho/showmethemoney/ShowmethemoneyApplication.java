package pt.rho.showmethemoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ShowmethemoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowmethemoneyApplication.class, args);
    }
}
