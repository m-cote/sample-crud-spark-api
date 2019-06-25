package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity(name = "UserAttributes")
@Table(name = "user_attributes")
public class UserAttribute {

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @JsonIgnore
    @EmbeddedId
    private Id id;

//    @Column(name = "key", insertable = false, updatable = false, nullable = false)
//    private String key;
    private String value;

    public UserAttribute() {
    }

    public UserAttribute(Integer userId, String key, String value) {
        this.id = new Id(userId, key);
        this.value = value;
    }

    public String getKey(){
        return id.getKey();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "user_id")
        private Integer userId;

        @Column(name = "`key`")
        private String key;
    }

}

