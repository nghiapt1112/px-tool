package com.px.tool.domain.user.payload;

import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.payload.AbstractObject;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest extends AbstractObject {
    private Long userId;
    private String email;
    private String password;
    private Integer level;
    private String imgBase64;
    private String fullName;

    private Long phongBanId;

    public Integer getLevel() {
        return level != null ? level : 2; // defaullt thi setting role la giam doc, truong phong.
    }

    public User toUserEntity() {
        User entity = new User();
        entity.setUserId(userId);
        entity.setEmail(email);
        entity.setSignImg(imgBase64);
        entity.setFullName(fullName);
        return entity;
    }
}