package http.enums;

public enum HttpStatus {
    OK(200),
    ;

    private static final String DESCRIPTION_FORMAT = "%s %s";
    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public String description() {
        return String.format(DESCRIPTION_FORMAT, code, name());
    }
}
