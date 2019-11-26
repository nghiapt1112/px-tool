package com.px.tool.domain.request.payload;

import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoiNhan extends AbstractObject {
    private Long id;
    private String name;

    public static NoiNhan fromUserEntity(User phongBan) {
        NoiNhan noiNhan = new NoiNhan();
        noiNhan.id = phongBan.getUserId();
        noiNhan.name = phongBan.getFullName();
        return noiNhan;
    }

}
