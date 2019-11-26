package com.px.tool.domain.request.payload;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ThongKePayload extends AbstractObject {
    private String sanPham;
    private String tienDo;
    private List<ThongKeDetailPayload> details;
    private Integer total;
    private Integer page;
    private Integer size;
}
