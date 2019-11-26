package com.px.tool.domain.request.payload;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThongKeRequest extends AbstractObject {
    private Integer page;
    private Integer size;
    private String sanPham;

    public void setPage(Integer page) {
        if (page == null || page <= 0) {
            this.page = 0;
        } else {
            this.page = page - 1;
        }
    }
}
