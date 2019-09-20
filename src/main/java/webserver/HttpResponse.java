package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {

    private HttpStatus status;
    private MediaType contentType;
    private final Map<String, String> headers;
    private byte[] body;

    public HttpResponse() {
        this.status = null;
        this.contentType = null;
        this.headers = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Set<String> getHeaderKeys() {
        return headers.keySet();
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public MediaType getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }
}
