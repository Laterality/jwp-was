package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private final String id;
    private final Map<String, Object> attributes;
    private boolean validity;

    private HttpSession(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
        this.validity = true;
    }

    public static HttpSession create() {
        return new HttpSession(UUID.randomUUID().toString(), new HashMap<>());
    }

    public String getId() {
        return id;
    }

    public void setAttributes(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
        validity = false;
    }

    public boolean isValid() {
        return validity;
    }
}
