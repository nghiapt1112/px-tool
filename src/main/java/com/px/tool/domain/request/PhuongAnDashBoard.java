package com.px.tool.domain.request;

import com.px.tool.domain.RequestStatus;
import com.px.tool.domain.phuongan.PhuongAn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhuongAnDashBoard extends DashBoard {
    public static PhuongAnDashBoard fromEntity(PhuongAn phuongAn) {
        PhuongAnDashBoard phuongAnDashBoard = new PhuongAnDashBoard();
        phuongAnDashBoard.setKey(String.valueOf(phuongAn.getPaId()));
        phuongAnDashBoard.setNoiDung(phuongAn.getNoiDung());
        phuongAnDashBoard.setTrangThaiRequest(RequestStatus.DANG_CHO_DUYET);
        return phuongAnDashBoard;
    }
}
