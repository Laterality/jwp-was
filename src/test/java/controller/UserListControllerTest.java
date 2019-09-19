package controller;

import org.junit.jupiter.api.Test;
import webserver.RequestDispatcher;
import webserver.RequestParser;
import webserver.Response;
import webserver.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class UserListControllerTest {

    private static final String TEST_DIRECTORY = "./src/test/resources";

    @Test
    void loggedIn() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/GetUserList.txt"));
        Response res = RequestDispatcher.handle(RequestParser.parse(in));

        assertThat(res.getStatus()).isEqualTo(Status.OK);
        assertThat(new String(res.getBody())).contains("이메일");
    }

    @Test
    void loggedIn_failed() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "/GetUserListFailed.txt"));
        Response res = RequestDispatcher.handle(RequestParser.parse(in));

        assertThat(res.getStatus()).isEqualTo(Status.FOUND);
        assertThat(res.getHeader("Location")).isEqualTo("/user/login.html");
    }
}
