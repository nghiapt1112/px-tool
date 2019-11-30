package com.px.tool.domain.request.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThongKeRequest extends PageRequest {
    private Long sanPham;
    private Long phanXuong;
    
    public void setSanPham(Long sanPham) {
        this.sanPham = sanPham.equals(-1L) ? null : sanPham;
    }
}
