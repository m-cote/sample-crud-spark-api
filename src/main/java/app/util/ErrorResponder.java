package app.util;

import app.util.json.JsonTransformer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.HaltException;

import java.util.Objects;
import java.util.Optional;

import static spark.Spark.halt;

public class ErrorResponder {

    private static final Logger log = LoggerFactory.getLogger(ErrorResponder.class);

    private Integer statusCode;
    private String message;
    private Optional<Exception> cause;

    public ErrorResponder() {
        cause = Optional.empty();
    }

    public static ErrorResponder builder() {
        return new ErrorResponder();
    }

    public ErrorResponder statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ErrorResponder message(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponder cause(Exception cause) {
        this.cause = Optional.of(cause);
        return this;
    }

    public HaltException haltError() {
        Objects.requireNonNull(statusCode, "statusCode must not be null in case of error");
        Objects.requireNonNull(message, "message must not be null in case of error");
        try {
            return halt(statusCode, generateBody());
        } catch (JsonProcessingException e) {
            return halt(statusCode);
        }
    }

    public String asString() {
        try {
            return generateBody();
        } catch (JsonProcessingException e) {
            log.error("Failed handling Error response formatting", e);
            throw new RuntimeException(e);
        }
    }

    private String generateBody() throws JsonProcessingException {
        return new JsonTransformer().render(new ErrorDetail(
                statusCode,
                message,
                cause.map(Throwable::getMessage)));
    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ErrorDetail {
        private final int statusCode;
        private final String message;
        private final Optional<String> details;
    }
}
