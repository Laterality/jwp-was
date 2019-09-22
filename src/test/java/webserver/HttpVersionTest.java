package webserver;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpVersionTest {

    @Test
    void from() {
        String input = "HTTP/1.1";
        Optional<HttpVersion> version = HttpVersion.from(input);

        assertThat(version.isPresent()).isTrue();
        assertThat(version.get().getMajor()).isEqualTo(1);
        assertThat(version.get().getMinor()).isEqualTo(1);
    }
}
