package com.px.tool.model.request;

import com.px.tool.model.AbstractObject;
import com.px.tool.model.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest extends AbstractObject {
    private String userName;
    private String password;

    public User toUserEntity() {
        User entity = new User();
        entity.setEmail(userName);
//        entity.setPassword(); // update password with passwordEncoder
        return entity;
    }
}
