package app.util.exception;

public class JsonPayloadParseException extends Exception {
    public JsonPayloadParseException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }

    public JsonPayloadParseException(String message) {
        super(message);
    }
}