package ru.javamentor.restapi;

import controller.APIController;
import model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;

@SpringBootApplication
public class RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);

		APIController apiController = new APIController();
		HttpHeaders headers = apiController.getUsers();

		String code = apiController.postUser(headers, new User(3L, "James", "Brown", (byte) 1));
		System.out.println("code: " + code);

		code += apiController.putUser(headers, new User(3L, "Thomas", "Shelby", (byte) 1));
		System.out.println("code: " + code);

		code += apiController.deleteUser(headers, 3L);
		System.out.println("code: " + code);
	}
}
