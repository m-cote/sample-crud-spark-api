package app.util.to;

import app.controller.UserAttributesPayload;
import app.model.UserAttribute;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAttributesUtil {

    public static List<UserAttribute> fromPayload(UserAttributesPayload payload, Integer userId) {

        return payload.entrySet().stream()
                .map(mapEntry -> new UserAttribute(userId, mapEntry.getKey(), mapEntry.getValue()))
                .collect(Collectors.toList());
    }

    public static UserAttributesPayload asPayload(List<UserAttribute> attributes) {
        final UserAttributesPayload result = new UserAttributesPayload();
        attributes.forEach(userAttribute -> result.put(userAttribute.getKey(), userAttribute.getValue()));
        return result;
    }

    public static UserAttributesPayload asPayload(UserAttribute userAttribute) {
        return asPayload(Collections.singletonList(userAttribute));
    }


}
