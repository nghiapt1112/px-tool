package com.px.tool.domain.vanbanden.payload;

import com.px.tool.infrastructure.model.payload.AbstractObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class PageVanBanDenPayload extends AbstractObject {
    private List<VanBanDenResponse> details;
    private Integer total;
    private Integer page;
    private Integer size;

    public PageVanBanDenPayload() {
        this.details = new ArrayList<>();
        this.total = 0;
    }

    public PageVanBanDenPayload withPage(Integer page) {
        this.page = page;
        return this;
    }

    public PageVanBanDenPayload withSize(Integer size) {
        this.size = size;
        return this;
    }
}
