package com.px.tool.domain.vanbanden.payload;

import com.px.tool.domain.vanbanden.VanBanDen;
import com.px.tool.infrastructure.model.payload.PageRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class VanBanDenPageRequest extends PageRequest {
    public VanBanDenPageRequest(Integer page, Integer size) {
        super(page, size);
    }

    @Override
    public org.springframework.data.domain.PageRequest toPageRequest() {
        return org.springframework.data.domain.PageRequest.of(page, size, Sort.by("createdAt").descending());


    }
}
