package app.util;

import app.model.HasId;
import app.util.exception.IllegalPayloadException;

public class ValidationUtil {

    public static void checkIsNew(HasId entity) throws IllegalPayloadException {
        if (!entity.isNew()) {
            throw new IllegalPayloadException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistency(HasId entity, int id) throws IllegalPayloadException {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalPayloadException(entity + " must be with id=" + id);
        }
    }


}
