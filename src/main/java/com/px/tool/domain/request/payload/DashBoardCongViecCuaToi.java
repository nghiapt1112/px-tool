package com.px.tool.domain.request.payload;

import com.px.tool.domain.RequestStatus;
import com.px.tool.domain.RequestType;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Getter
@Setter
public class DashBoardCongViecCuaToi extends AbstractObject {
    private String ma;
    private long requestId;
    private String noiDung;
    private RequestStatus status;
    private RequestType type;

    public static DashBoardCongViecCuaToi fromEntity(Request request, Map<Long, User> userById) {
        DashBoardCongViecCuaToi dashBoardCongViecCuaToi = new DashBoardCongViecCuaToi();
        dashBoardCongViecCuaToi.ma = "Key-" + request.getRequestId();
        dashBoardCongViecCuaToi.requestId = request.getRequestId();
        dashBoardCongViecCuaToi.type = request.getType();

        dashBoardCongViecCuaToi.noiDung = "Gửi từ phân xưởng: " + getVal(userById, request.getKiemHong().getPhanXuong()) + "- Tổ sản xuất: " + getVal(userById, request.getKiemHong().getToSX());
        dashBoardCongViecCuaToi.status = RequestStatus.DANG_CHO_DUYET;
        return dashBoardCongViecCuaToi;
    }

    private static String getVal(Map<Long, User> unameById, Long key) {
        if (CollectionUtils.isEmpty(unameById)) {
            return key.toString();
        }
        Long k = Long.valueOf(key);
        if (unameById.containsKey(k)) {
            return unameById.get(k) == null ? key.toString() : unameById.get(k).getFullName();
        }
        return key.toString();
    }
}
