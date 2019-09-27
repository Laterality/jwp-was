package webserver;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class StaticFileResolverTest {

    @Test
    void create() throws URISyntaxException {
        StaticFileResolver staticFileResolver = new StaticFileResolver("static");
    }

    @Test
    void is_available() throws URISyntaxException {
        StaticFileResolver staticFileResolver = new StaticFileResolver("static");
        assertThat(staticFileResolver.isAvailable("/css/styles.css")).isTrue();
    }

    @Test
    void get_path() throws URISyntaxException {
        StaticFileResolver staticFileResolver = new StaticFileResolver("static");
        assertDoesNotThrow(() -> {
            assertThat(Files.readAllBytes(staticFileResolver.getFilePath("/css/styles.css"))).isNotNull();
        });
    }
}
