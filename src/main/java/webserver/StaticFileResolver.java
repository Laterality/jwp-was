package webserver;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticFileResolver {

    private final URI rootDir;

    public StaticFileResolver(String dir) throws URISyntaxException {
        this.rootDir = StaticFileResolver.class.getClassLoader().getResource(dir + "/").toURI();
    }

    public boolean isAvailable(String path) {
        return Files.exists(Paths.get(rootDir.resolve("." + path)));
    }

    public Path getFilePath(String urlPath) {
        return Paths.get(rootDir.resolve("." + urlPath));
    }
}
