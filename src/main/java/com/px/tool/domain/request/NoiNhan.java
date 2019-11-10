package com.px.tool.domain.request;

import com.px.tool.domain.user.PhongBan;
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

    public static NoiNhan fromPhongBanEntity(PhongBan phongBan) {
        NoiNhan noiNhan = new NoiNhan();
        noiNhan.id = phongBan.getPhongBanId();
        noiNhan.name = phongBan.getName();
        return noiNhan;
    }

    public static NoiNhan fromUserEntity(User phongBan) {
        NoiNhan noiNhan = new NoiNhan();
        noiNhan.id = phongBan.getUserId();
        noiNhan.name = phongBan.getUsername();
        return noiNhan;
    }
}
