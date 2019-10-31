package com.px.tool.domain.user;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPayload extends AbstractObject {
    public Long userId;
    private String email;
//    private String password;

    public static UserPayload fromEntity(User user) {
        UserPayload userPayload = new UserPayload();
        userPayload.userId = user.getUserId();
        userPayload.email = user.getEmail();
        return userPayload;
    }
}
