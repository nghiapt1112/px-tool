package com.px.tool.domain.user.payload;

import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.payload.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

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
    private Long phanXuong;

    public Integer getLevel() {
        return level != null ? level : 2; // defaullt thi setting role la giam doc, truong phong.
    }

    public User toUserEntity() {
        User entity = new User();
        entity.setUserId(userId);
        if (!StringUtils.isEmpty(email)) {
            entity.setEmail(email);
        }
        if (!StringUtils.isEmpty(imgBase64)) {
            entity.setSignImg(imgBase64);
        }
        if (!StringUtils.isEmpty(fullName)) {
            entity.setFullName(fullName);
        }
        return entity;
    }
}
