package com.px.tool.infrastructure.model.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class AbstractPageResponse<O> extends AbstractObject {
    protected List<O> details;
    protected Long total;
    protected Integer page;
    protected Integer size;

    public AbstractPageResponse(Integer page, Integer size) {
        super();
        this.page = page;
        this.size = size;
    }

    private AbstractPageResponse() {
        this.details = new ArrayList<>();
        this.total = 0L;
    }

    public void setTotal(Integer size) {
        this.total = total;
    }

    public <E extends AbstractObject> E build() {
        return (E) this;
    }

    public void setDetails(List<O> details) {
        this.details = details;
    }
}
