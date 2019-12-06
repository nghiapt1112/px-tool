package com.px.tool.domain.vanbanden.payload;

import com.px.tool.infrastructure.model.payload.AbstractPage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PageVanBanDenPayload extends AbstractPage<VanBanDenResponse> {

    public PageVanBanDenPayload(Integer page, Integer size) {
        super(page, size);
    }

    @Override
    public PageVanBanDenPayload build() {
        return this;
    }
}
