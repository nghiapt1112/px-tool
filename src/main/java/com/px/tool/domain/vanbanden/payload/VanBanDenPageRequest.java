package com.px.tool.domain.vanbanden.payload;

import com.px.tool.domain.request.payload.PageRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VanBanDenPageRequest extends PageRequest {
    public VanBanDenPageRequest(Integer page, Integer size) {
        super(page, size);
    }
}
