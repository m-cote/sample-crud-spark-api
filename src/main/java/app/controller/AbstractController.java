package app.controller;

import app.util.ErrorResponder;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Response;

public class AbstractController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected String noContentResponse(Response response) {
        response.status(HttpStatus.NO_CONTENT_204);
        return "";
    }

    protected void badRequestResponse(String message, Exception e) {
        log.info(message, e);
        throw ErrorResponder.builder()
                .statusCode(HttpStatus.BAD_REQUEST_400)
                .message(message)
                .cause(e)
                .haltError();
    }
}
