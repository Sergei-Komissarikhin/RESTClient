package com.sergei;

import com.sergei.entity.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Hello world!
 *
 */

public class App {

    private static final String URL = "http://91.241.64.178:7081/api/users";

    public static void main( String[] args ) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {
                        });
        HttpHeaders headersWithCookies = new HttpHeaders();
        headersWithCookies.set("Cookie", responseEntity.getHeaders().getFirst("Set-Cookie"));

        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte)3);

        HttpEntity<User> entity = new HttpEntity(user,headersWithCookies);

        ResponseEntity<String> responseEntity1 = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        String first = responseEntity1.getBody();
        user.setName("Thomas");
        user.setLastName("Shelby");

        entity = new HttpEntity(user,headersWithCookies);

        ResponseEntity<String> responseEntity2 = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        String second = responseEntity2.getBody();

        entity = new HttpEntity(user,headersWithCookies);
        ResponseEntity<String> responseEntity3 = restTemplate.exchange((URL + "/" + 3), HttpMethod.DELETE, entity, String.class);
        String third = responseEntity3.getBody();
        System.out.println("Answer is : " + first + second + third);
    }
}
