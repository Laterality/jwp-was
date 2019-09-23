package webserver;

import java.util.Map;

public class HttpRequest {

    private final HttpMethod method;
    private final String url;
    private final String path;
    private final Map<String, String> queries;
    private final Map<String, String> headers;
    private final Map<String, String> cookies;
    private final byte[] body;
    private HttpSession session;

    HttpRequest(HttpMethod method, String url, String path, Map<String, String> queries,
                       Map<String, String> headers, Map<String, String> cookies, byte[] body) {
        this.method = method;
        this.url = url;
        this.path = path;
        this.queries = queries;
        this.headers = headers;
        this.cookies = cookies;
        this.body = body;

        if (headers.containsKey("sid")) {
            session = SessionManager.getSession(headers.get("sid"));
        }
    }

    public boolean matchMethod(HttpMethod method) {
        return this.method.equals(method);
    }

    public HttpSession getSession() {
        if (session == null || !session.isInvalid()) {
            session = HttpSession.createSession();
            SessionManager.addSession(session);
        }
        return session;
    }

    public boolean hasValidSession() {
        return session != null && !session.isInvalid();
    }

    public String getQuery(String key) {
        return queries.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public byte[] getBody() {
        return body;
    }
}