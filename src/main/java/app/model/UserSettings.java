package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "UserSettings")
@Table(name = "user_settings")
public class UserSettings implements HasId, Validable{

    @Id
    protected Integer id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "send_sms")
    private boolean sendSms = false;
    @Column(name = "send_email")
    private boolean sendEmail = false;

    @Override
    public boolean isNew() {
        return id == null;
    }

}
