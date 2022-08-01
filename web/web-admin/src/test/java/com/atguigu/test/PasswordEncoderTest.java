package com.atguigu.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author nicc
 * @version 1.0
 * @className PasswardEncoderTest
 * @description TODO
 * @date 2022-07-30 14:58
 */
public class PasswordEncoderTest {

    @Test
    public void test(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("654321"));

        System.out.println(passwordEncoder.matches("123456", "$2a$10$YJJl7.6HyNs4ejiLFnLmzu/tLCSYinZCCXRKmXxl2j.Ym4a1/.rDK"));
    }
}
