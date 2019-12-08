package com.px.tool.domain.vanbanden.payload;

import com.px.tool.infrastructure.model.payload.AbstractPageResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VanBanDenPageResponse extends AbstractPageResponse<VanBanDenResponse> {

    public VanBanDenPageResponse(Integer page, Integer size) {
        super(page, size);
    }

    @Override
    public VanBanDenPageResponse build() {
        return this;
    }
}
