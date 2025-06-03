/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package E2.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
/**
 *
 * @author quant
 */
@RestCibtroller
public class DataController {

    private final RestTemplate restTemplate;
    public DataController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @GetMapping("/data")
    public String getData() {
        String url = "https://sse.dev/test?interval=1";
        return restTemplate.getForObject(url, String.class);
    }
}