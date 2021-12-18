package com.sergei;

import com.sergei.config.MyConfig;
import com.sergei.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Hello world!
 *
 */

public class App {

    public static void main( String[] args ) {

        RestTemplate restTemplate = new RestTemplate();

        String URL = "http://91.241.64.178:7081/api/users";

        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {
                        });

        HttpHeaders headersWithCookies = new HttpHeaders();
        headersWithCookies.set("Set-Cookie", responseEntity.getHeaders().getFirst("Set-Cookie"));
        System.out.println(headersWithCookies);
        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte)3);
        HttpEntity<User> entity = new HttpEntity(user,headersWithCookies);
        ResponseEntity<String> responseEntity1 = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        System.out.println(responseEntity1.getBody());
        user.setName("Thomas");
        user.setLastName("Shelby");

        headersWithCookies.set("Set-Cookie", responseEntity1.getHeaders().getFirst("Set-Cookie"));
        entity = new HttpEntity(user,headersWithCookies);
        ResponseEntity<String> responseEntity2 = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        System.out.println(responseEntity2.getBody());
        ResponseEntity<String> responseEntity3 = restTemplate.exchange((URL + "/" + 3), HttpMethod.DELETE, entity, String.class);
        System.out.println(responseEntity3.getBody());
    }
}
