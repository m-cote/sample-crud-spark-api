package app.util;

import app.util.exception.IllegalRequestParameterException;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import spark.Request;

public class RequestUtil {

    private RequestUtil() {
    }

    public static int getParamUserId(Request request, Logger log) {
        try {
            return getPositiveInteger(request, "userId");
        } catch (IllegalRequestParameterException e) {
            log.info("Invalid user path", e);
            throw ErrorResponder.builder()
                    .statusCode(HttpStatus.BAD_REQUEST_400)
                    .message("Invalid user path")
                    .cause(e)
                    .haltError();
        }
    }

    private static int getPositiveInteger(Request request, String parameterName) throws IllegalRequestParameterException {
        String stringValue = request.params(parameterName);
        if (stringValue == null) {
            throw new IllegalRequestParameterException("Parameter " + parameterName + " is missing");
        }

        int value;
        try {
            value = Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            throw new IllegalRequestParameterException("Can not parse value " + stringValue);
        }

        if (value < 0) {
            throw new IllegalRequestParameterException(parameterName + " must not be negative");
        }

        if (value == 0) {
            throw new IllegalRequestParameterException(parameterName + " must not be equal to zero");
        }

        return value;
    }

}
