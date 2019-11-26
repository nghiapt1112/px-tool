package com.px.tool.domain.request.payload;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PageRequest extends AbstractObject {
    protected Integer page;
    protected Integer size;

    public PageRequest(Integer page, Integer size) {
        setPage(page);
        this.size = size;
    }

    public void setPage(Integer page) {
        if (page == null || page <= 0) {
            this.page = 0;
        } else {
            this.page = page - 1;
        }
    }
}
