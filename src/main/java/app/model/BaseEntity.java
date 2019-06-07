package app.model;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseEntity {

    @Getter
    @Setter
    private Long id;

    public boolean isNew() {
        return id == null;
    }
}
