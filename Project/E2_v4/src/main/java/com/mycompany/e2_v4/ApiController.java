/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.e2_v4;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
/**
 *
 * @author quant
 */
@RestController
public class ApiController {
    private final RestTemplate restTemplate;
    public ApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @GetMapping("/api/data")
    public String getData() {
        String url = "https://sse.dev/test?interval=1";
        return restTemplate.getForObject(url, String.class);
    }
}