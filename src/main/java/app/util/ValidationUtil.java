package app.util;

import app.model.BaseEntity;
import app.util.exception.IllegalRequestDataException;

public class ValidationUtil {

    public static void checkIsNew(BaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistency(BaseEntity entity, long id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalRequestDataException(entity + " must be with id=" + id);
        }
    }


}
