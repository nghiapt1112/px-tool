package com.px.tool.domain.request;

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
}
