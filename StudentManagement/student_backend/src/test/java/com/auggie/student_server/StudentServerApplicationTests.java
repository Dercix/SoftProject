package com.auggie.student_server;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class StudentServerApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123"));
    }

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void getPass() {
        String password = encryptor.encrypt("password");
        System.out.println("---" + password + "---");
        System.out.println("---" + encryptor.decrypt(password) + "---");
    }

}
