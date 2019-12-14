package com.px.tool.domain.vanbanden.payload;

import com.px.tool.domain.vanbanden.VanBanDen;
import com.px.tool.infrastructure.model.payload.AbstractPageResponse;
import com.px.tool.infrastructure.utils.CommonUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.px.tool.infrastructure.utils.CommonUtils.toCollection;

@Getter
@Setter

public class VanBanDenPageResponse extends AbstractPageResponse<VanBanDenResponse> {

    public VanBanDenPageResponse(Integer page, Integer size) {
        super(page, size);
    }

    public void parse(List<VanBanDen> details, Map<Long, String> noiNhanById) {
        this.details = details.stream()
                .map(el -> {
                    VanBanDenResponse payload = VanBanDenResponse.fromEntity(el);
                    payload.setNoiNhan(CommonUtils.toString(toCollection(el.getNoiNhan()), noiNhanById));
                    return payload;
                })
                .collect(Collectors.toList());
    }
}
