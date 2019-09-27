package controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

public class LoginControllerTest extends ControllerTestHelper {

    @Test
    void login() {
        webTestClient.post()
            .uri(LoginController.USER_LOGIN_URL)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData("userId", "john123")
                .with("password", "p@ssW0rd"))
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueMatches("Location", "/index.html");
    }

    @Test
    void login_failed2() {
        webTestClient.post()
            .uri(LoginController.USER_LOGIN_URL)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(BodyInserters.fromFormData("userId", "john123")
                .with("password", "p@ssW0r"))
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueMatches("Location", "/user/login_failed.html");
    }
}
