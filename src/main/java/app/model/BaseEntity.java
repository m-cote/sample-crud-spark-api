package app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseEntity implements HasId{

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    public boolean isNew() {
        return id == null;
    }
}
