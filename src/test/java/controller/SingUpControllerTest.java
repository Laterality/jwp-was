package controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

public class SingUpControllerTest extends ControllerTestHelper {

    @Test
    void create() {
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

}
