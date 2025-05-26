/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.e2_v4;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 *
 * @author quant
 */
@Configuration
public class RestTemplate {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
