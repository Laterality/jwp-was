package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @Test
    void create() {
        String requestLine = "GET /index.html HTTP/1.1";
        // TODO: 2019-09-23  
        RequestLine rl = RequestLine.from(requestLine);
//        assertThat(rl.getMethod()).isEqualTo(HttpMethod.GET);
//        assertThat(rl.getPath()).isEqualTo("/index.html");
//        assertThat(rl.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
    }
}
