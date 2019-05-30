package org.ordersample.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${orderservice.endpoint}")
    private String orderServiceURL;

    @GetMapping("/OrderService/{orderId}")
    public String getOrder(@PathVariable("orderId") String orderId) throws BadRequestException{

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            ResponseEntity<String> responseEntity = restTemplate.exchange(orderServiceURL + "/order/" + orderId, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
            return responseEntity.getBody();
        }catch (HttpClientErrorException ex){
            throw new BadRequestException("Order ID does not exist");
        }

    }
}
