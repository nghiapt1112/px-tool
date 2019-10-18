package com.px.tool.model.request;

import com.px.tool.model.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest extends AbstractObject {
    private String userName;
    private String password;

}
