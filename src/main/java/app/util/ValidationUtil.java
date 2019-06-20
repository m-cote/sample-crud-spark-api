package app.util;

import app.model.HasId;
import app.util.exception.IllegalPayloadException;
import app.util.exception.NotFoundException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkIsNew(HasId entity) throws IllegalPayloadException {
        if (!entity.isNew()) {
            throw new IllegalPayloadException(entity + " must be new (id=null)");
        }
    }

    public static void setEntityId(HasId entity, int id) throws IllegalPayloadException {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalPayloadException(entity + " must be with id=" + id);
        }
    }

    public static void checkNotFoundWithId(Object object, String message) throws NotFoundException {
        if (object == null) {
            throw new NotFoundException(message);
        }
    }

    public static void checkNotFoundWithId(boolean isFound, String message) throws NotFoundException {
        if (!isFound) {
            throw new NotFoundException(message);
        }
    }

}
