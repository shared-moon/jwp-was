package http.io;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class HttpCookies {
    private Map<String, HttpCookie> cookiesByKey = new ConcurrentHashMap<>();

    public HttpCookies() {}

    public HttpCookies(List<HttpCookie> cookies) {
        this.cookiesByKey = cookies.stream().collect(toMap(HttpCookie::getKey, identity(), (e1, e2) -> e1));
    }

    public void add(HttpCookie cookie) {
        cookiesByKey.put(cookie.getKey(), cookie);
    }

    public Set<String> keySet() {
        return cookiesByKey.keySet();
    }

    public HttpCookie getCookie(String key) {
        return cookiesByKey.get(key);
    }

    public String getValue(String key) {
        if (!cookiesByKey.containsKey(key)) {
            return null;
        }
        return cookiesByKey.get(key).getValue();
    }

    public boolean isEmpty() {
        return cookiesByKey.isEmpty();
    }
}
