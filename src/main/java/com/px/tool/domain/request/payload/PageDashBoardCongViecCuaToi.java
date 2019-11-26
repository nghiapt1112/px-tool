package com.px.tool.domain.request.payload;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDashBoardCongViecCuaToi extends AbstractObject {
    private List<DashBoardCongViecCuaToi> details;
    private Integer total;
    private Integer page;
    private Integer size;

}
