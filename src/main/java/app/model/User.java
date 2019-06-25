package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import spark.utils.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString(exclude = "settings,attributes")
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Validable{

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    private UserSettings settings;

/*
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = false, fetch = FetchType.LAZY)
    private List<UserAttribute> attributes;
*/

    public User() {
    }

    public User(@NotBlank String firstName, String lastName) {
        this(null, firstName, lastName);
    }

    public User(Integer id, @NotBlank String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setSettings(UserSettings settings) {
        this.settings = settings;
        settings.setUser(this);
    }

    @Override
    public boolean isValid() {
        return lastName != null && StringUtils.isNotBlank(firstName);
    }
}

