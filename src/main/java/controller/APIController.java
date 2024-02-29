package controller;

import model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class APIController {
    private final String url = "http://94.198.50.185:7081/api/users";

    @GetMapping
    public HttpHeaders getUsers() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(url, User[].class);
        User[] objectsArray = responseEntity.getBody();
        List<User> users = Arrays.asList(objectsArray);
        users.forEach(System.out::println);

        // Получаем  cookies
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        cookies.forEach(System.out::println);

        // Сохраняем  cookies в заголовке
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie",String.join(";",cookies));
//        headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));
//        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println(headers);
        return headers;
    }

    @PostMapping
    public String postUser(HttpHeaders headers, User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        return restTemplate.postForObject(url, requestEntity, String.class);
    }

    @PutMapping
    public String putUser(HttpHeaders headers, User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class).getBody();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(HttpHeaders headers, @PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String urlDelete = url + "/" + id;
        return restTemplate.exchange(urlDelete, HttpMethod.DELETE, requestEntity, String.class).getBody();
    }
}
