package webserver;

import utils.UrlEncodedParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestParser {

    private static final int MAX_BODY_SIZE = 1024 * 100; // 100KB
    private static final String HEADER_DELIMITER = ": ";
    private static final int HEADER_SPLIT_LIMIT = 2;
    private static final String FIRST_LINE_DELIMITER = " ";
    private static final String PATH_QUERY_DELIMITER = "?";
    private static final String PATH_QUERY_DELIMITER_REGEX = "\\?";
    private static final String COOKIE_HEADER_KEY = "Cookie";
    private static final String COOKIE_PAIR_DELIMITER = "; ";

    public static Request parse(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String[] firstLine = br.readLine().split(FIRST_LINE_DELIMITER);
        HttpMethod method = HttpMethod.from(firstLine[0]).
                orElseThrow(() -> new IllegalArgumentException("Unsupported http method: " + firstLine[0]));
        String url = firstLine[1].split(PATH_QUERY_DELIMITER_REGEX)[0];

        Map<String, String> queries = new HashMap<>();
        parseQueryString(firstLine[1], queries);

        Map<String, String> headers = new HashMap<>();
        parseHeader(br, headers);

        Map<String, String> cookies = new HashMap<>();
        parseCookie(headers, cookies);

        char[] buf = new char[MAX_BODY_SIZE];
        readToBuffer(br, buf);

        return new Request(method, url, queries, headers, cookies, new String(buf).getBytes());
    }

    private static void parseQueryString(String pair, Map<String, String> queries) {
        if (pair.contains(PATH_QUERY_DELIMITER)) {
            String[] queryPairs = pair.split(PATH_QUERY_DELIMITER_REGEX);
            queries.putAll(UrlEncodedParser.parse(queryPairs[1]));
        }
    }

    private static void parseHeader(BufferedReader br, Map<String, String> headers) throws IOException {
        String line = br.readLine();
        while (hasMoreLine(line)) {
            String[] headerTokens = line.split(HEADER_DELIMITER, HEADER_SPLIT_LIMIT);
            headers.put(headerTokens[0], headerTokens[1]);
            line = br.readLine();
        }
    }

    private static boolean hasMoreLine(String line) {
        return !(line == null || line.isEmpty());
    }

    private static void parseCookie(Map<String, String> headers, Map<String, String> cookies) {
        if (headers.containsKey(COOKIE_HEADER_KEY)) {
            String[] tokens = headers.get(COOKIE_HEADER_KEY).split(COOKIE_PAIR_DELIMITER);
            Arrays.stream(tokens)
                    .map(UrlEncodedParser::parsePairWithEqualSign)
                    .filter(Objects::nonNull)
                    .forEach(tuple -> cookies.put(tuple.getKey(), tuple.getValue()));
        }
    }

    private static void readToBuffer(BufferedReader br, char[] buf) throws IOException {
        if (br.ready()) {
            br.read(buf);
        }
    }
}
