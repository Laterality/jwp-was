package webserver;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

public class RequestParserTest {

    @Test
    void get_request() {
        byte[] input = ("GET /index.html HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Accept: */*").getBytes();

        // TODO: 2019-09-23  
//        HttpRequest req = HttpRequestParser.parse(new ByteArrayInputStream(input));
    }
}
