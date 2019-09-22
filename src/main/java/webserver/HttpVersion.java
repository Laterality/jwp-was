package webserver;

import java.util.Arrays;
import java.util.Optional;

public enum HttpVersion {
    HTTP_1_0(1, 0),
    HTTP_1_1(1, 1),
    HTTP_2_0(2, 0);

    private final int major;
    private final int minor;

    HttpVersion(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public static Optional<HttpVersion> from(String version) {
        String[] versionTokens = version.split("/")[1].split("\\.");
        int major = Integer.parseInt(versionTokens[0]);
        int minor = Integer.parseInt(versionTokens[1]);

        return Arrays.stream(values())
            .filter(value -> value.getMajor() == major)
            .filter(value -> value.getMinor() == minor)
            .findFirst();
    }
}
