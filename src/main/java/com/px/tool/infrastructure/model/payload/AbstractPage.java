package com.px.tool.infrastructure.model.payload;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPage<O> extends AbstractObject {
    protected List<O> details;
    protected Integer total;
    protected Integer page;
    protected Integer size;

    public AbstractPage(Integer page, Integer size) {
        super();
        this.page = page;
        this.size = size;
    }

    public AbstractPage() {
        this.details = new ArrayList<>();
        this.total = 0;
    }

    public void setTotal(Integer size) {
        this.total = total;
    }

    public abstract <E extends AbstractObject>  E build();

    public void setDetails(List<O> details) {
        this.details = details;
    }
}
