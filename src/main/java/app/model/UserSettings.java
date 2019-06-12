package app.model;

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
public class UserSettings implements HasId{

    @Getter
    @Setter
    @Id
    protected Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private boolean sendSms = false;
    private boolean sendEmail = false;

    public boolean isNew() {
        return id == null;
    }


}
