package app.util.exception;

public class IllegalRequestParameterException extends Exception {
    public IllegalRequestParameterException(String msg) {
        super(msg);
    }
}