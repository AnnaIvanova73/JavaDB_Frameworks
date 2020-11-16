package com.softuni.springintroex.config;

import com.softuni.springintroex.utils.FileUtil;
import com.softuni.springintroex.utils.FileUtilImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class BeanConfiguration {

    @Bean
    public BufferedReader reader(){
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public StringBuilder builder (){
        return new StringBuilder();
    }
}
