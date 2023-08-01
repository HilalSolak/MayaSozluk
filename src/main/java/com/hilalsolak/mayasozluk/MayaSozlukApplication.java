package com.hilalsolak.mayasozluk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MayaSozlukApplication {

    public static void main(String[] args) {
        SpringApplication.run(MayaSozlukApplication.class, args);
    }

}
