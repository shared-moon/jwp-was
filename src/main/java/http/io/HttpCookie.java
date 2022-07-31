package http.io;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HttpCookie {
    private final String key;
    private final String value;
    private final List<String> options = new ArrayList<>();

    private HttpCookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static HttpCookie of(String key, Object value) {
        return new HttpCookie(key, String.valueOf(value));
    }

    public void addOption(String option) {
        this.options.add(option);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        LinkedList<String> mergeList = new LinkedList<>(options);
        mergeList.addFirst(key + "=" + value);
        return String.join("; ", mergeList);
    }
}
