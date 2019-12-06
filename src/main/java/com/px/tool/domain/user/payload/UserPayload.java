package com.px.tool.domain.user.payload;

import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.payload.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPayload extends AbstractObject {
    public Long userId;
    private String email;
    private String signImg;
    private String fullName;
    // external fields for [admin]-list-users
    private String phanXuong;
    private String level;

    public static UserPayload fromEntity(User user) {
        UserPayload userPayload = new UserPayload();
        userPayload.userId = user.getUserId();
        userPayload.email = user.getEmail();
        userPayload.signImg = user.getSignImg();
        userPayload.fullName = user.getFullName();
        return userPayload;
    }
}
