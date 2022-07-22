package http.io;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public class HttpRequestHeader {
    private static final String DELIMITER = ":";
    private static final String NEXT_LINE = "\r\n";
    private static final int IDX_KEY = 0;
    private static final int IDX_VALUE = 1;
    private final Map<String, String> headers = new HashMap<>();

    public void add(String line) {
        String[] header = line.split(DELIMITER);
        headers.put(header[IDX_KEY].trim(), header[IDX_VALUE].trim());
    }

    public boolean containsKey(String key) {
        return headers.containsKey(key);
    }

    public String get(String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        return headers.entrySet().stream()
                .map(entry -> entry.getKey() + DELIMITER + " " + entry.getValue())
                .collect(joining(NEXT_LINE));
    }
}
