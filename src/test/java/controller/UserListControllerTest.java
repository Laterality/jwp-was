package controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest extends ControllerTestHelper {

    @Test
    void logged_id() {
        webTestClient.get()
            .uri(UserListController.USER_LIST_URL)
            .cookie("logined", "true")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(res -> {
                assertThat(new String(res.getResponseBody()))
                    .contains("이메일");
            });
    }

    @Test
    void logged_in_failed() {
        webTestClient.get()
            .uri(UserListController.USER_LIST_URL)
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueMatches("Location", "/user/login.html");
    }
}
