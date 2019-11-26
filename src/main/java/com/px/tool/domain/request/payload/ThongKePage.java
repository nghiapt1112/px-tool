package com.px.tool.domain.request.payload;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ThongKePage extends AbstractObject {
    private List<ThongKePayload> details;
    private int total;
    private int page;
    private int size;
}
