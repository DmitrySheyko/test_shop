package com.example.test_shop.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AddAdmin implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Admin username: admin \nAdmin password: admin");

    }

}