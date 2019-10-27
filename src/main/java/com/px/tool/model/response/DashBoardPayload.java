package com.px.tool.model.response;

import com.px.tool.model.AbstractObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DashBoardPayload extends AbstractObject {
    private List<KiemHongDashBoard> kiemHongDashBoards;
    private List<DatHangDashBoard> datHangDashBoards;
    private List<PhuongAnDashBoard> phuongAnDashBoards;
    private List<CongNhanThanhPhamDashBoard> congNhanThanhPhamDashBoards;
}
