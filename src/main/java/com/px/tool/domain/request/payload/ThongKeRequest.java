package com.px.tool.domain.request.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThongKeRequest extends PageRequest {
    private String sanPham;
}
