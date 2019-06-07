package app.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Settings extends BaseEntity{

    private boolean sendSms = false;
    private boolean sendEmail = false;

}
