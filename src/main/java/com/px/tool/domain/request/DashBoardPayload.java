package com.px.tool.domain.request;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DashBoardPayload extends AbstractObject {
    private List<KiemHongDashBoard> kiemHongDashBoards;
    private List<DatHangDashBoard> datHangDashBoards;
    private List<PhuongAnDashBoard> phuongAnDashBoards;
    private List<CongNhanThanhPhamDashBoard> congNhanThanhPhamDashBoards;

    public DashBoardPayload() {
        kiemHongDashBoards = new ArrayList<>();
        datHangDashBoards = new ArrayList<>();
        phuongAnDashBoards = new ArrayList<>();
        congNhanThanhPhamDashBoards = new ArrayList<>();
    }


    public void fromEntity(Request request) {
        kiemHongDashBoards.add(KiemHongDashBoard.fromEntity(request.getKiemHong()));
        datHangDashBoards.add(DatHangDashBoard.fromEntity(request.getPhieuDatHang()));
        phuongAnDashBoards.add(PhuongAnDashBoard.fromEntity(request.getPhuongAn()));
        congNhanThanhPhamDashBoards.add(CongNhanThanhPhamDashBoard.fromEntity(request.getCongNhanThanhPham()));
    }
}
