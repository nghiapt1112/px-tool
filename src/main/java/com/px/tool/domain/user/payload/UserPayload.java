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
        UserPayload payload = new UserPayload();
        payload.userId = user.getUserId();
        payload.email = user.getEmail();
        payload.signImg = user.getSignImg();
        payload.fullName = user.getFullName();
        try {
            payload.setPhanXuong(user.getPhongBan().getName());
            payload.level = String.valueOf(user.getLevel());
        } catch (Exception e) {
        }
        return payload;
    }

    public static UserPayload fromEntityNoImg(User user) {
        UserPayload payload = new UserPayload();
        payload.userId = user.getUserId();
        payload.email = user.getEmail();
        payload.fullName = user.getFullName();
        try {
            payload.phanXuong = user.getPhongBan().getName() == null ? "" : user.getPhongBan().getName();
            payload.level = String.valueOf(user.getLevel());
        } catch (Exception e) {
        }
        return payload;
    }
}
