package com.laowengs;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class WebSecurityConfigTest {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }
}