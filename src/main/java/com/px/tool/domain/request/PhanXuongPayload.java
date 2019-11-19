package com.px.tool.domain.request;

import com.px.tool.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PhanXuongPayload {
    private Long id;
    private String name;

    public static PhanXuongPayload fromUserEntity(User user) {
        PhanXuongPayload payload = new PhanXuongPayload();
        payload.id = user.getUserId();
        payload.name = user.getFullName();
        return payload;
    }
}
