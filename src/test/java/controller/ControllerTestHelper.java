package controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import webserver.WebServer;

public class ControllerTestHelper {

    protected WebTestClient webTestClient;
    protected Thread serverThread;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        serverThread = new Thread(() -> WebServer.main(new String[0]));
        serverThread.start();
        webTestClient.post()
            .uri(SignUpController.USER_CREATE_URL)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData("userId", "john123")
                .with("password", "p@ssW0rd")
                .with("name", "john")
                .with("email", "john@example.com"))
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueMatches("Location", "/index.html")
            .expectBody().returnResult();
    }


    @AfterEach
    void cleanup() {
        serverThread.interrupt();
    }
}
