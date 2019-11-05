package com.px.tool.infrastructure.model.request;

import com.px.tool.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest extends AbstractObject {
    private String userName;
    private String password;
    private Integer level;

    public Integer getLevel() {
        return level != null ? level : 2; // defaullt thi setting role la giam doc, truong phong.
    }

    public User toUserEntity() {
        User entity = new User();
        entity.setEmail(userName);
//        entity.setPassword(); // update password with passwordEncoder
        return entity;
    }
}
