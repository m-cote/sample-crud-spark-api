package app.model;

import lombok.*;
import spark.utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Validable{

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
//    @JsonIgnore
//    private List<UserAttribute> attributes;

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

    @Override
    public boolean isValid() {
        return lastName != null && StringUtils.isNotBlank(firstName);
    }
}

