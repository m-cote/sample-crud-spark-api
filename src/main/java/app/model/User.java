package app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class User extends BaseEntity {

    private Settings settings;
    private Map<String, String> attributes;

    public User() {
        this(new Settings());
    }

    public User(Settings settings) {
        this(settings, new HashMap<>());
    }

    public User(Settings settings, Map<String, String> attributes) {
        this.settings = settings;
        this.attributes = attributes;
    }
}
