package com.px.tool.domain.request.payload;

import com.px.tool.infrastructure.model.payload.PageRequest;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThongKePageRequest extends PageRequest {
    private Long sanPham;
    private Long phanXuong;
    private Long fromDate;
    private Long toDate;

    public void setSanPham(Long sanPham) {
        this.sanPham = sanPham.equals(-1L) ? null : sanPham;
    }

    public void setToDate(Long toDate) {
        this.toDate = toDate;
        if (this.toDate == null || this.toDate.equals(-1L)) {
            this.toDate = DateTimeUtils.nowAsMilliSec();
        }
    }
}
