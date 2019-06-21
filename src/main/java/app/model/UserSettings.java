package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@Entity(name = "UserSettings")
@Table(name = "user_settings")
public class UserSettings implements HasId, Validable{

    @Id
    protected Integer id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @Column(name = "send_sms")
    private Boolean sendSms;
    @Column(name = "send_email")
    private Boolean sendEmail;

    public UserSettings(boolean sendSms, boolean sendEmail) {
        this.sendSms = sendSms;
        this.sendEmail = sendEmail;
    }

    public static UserSettings getDefault(){
        return new UserSettings(true, false);
    }


    @Override
    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean isValid() {
        return sendSms != null && sendEmail != null;
    }
}
