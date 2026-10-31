package com.alves.picpay;

import org.springframework.boot.SpringApplication;

public class TestPicPayApplication {

    public static void main(String[] args) {
        SpringApplication.from(PicPayApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
