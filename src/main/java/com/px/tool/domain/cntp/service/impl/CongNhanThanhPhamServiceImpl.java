package com.px.tool.domain.cntp.service.impl;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;
import com.px.tool.domain.cntp.NoiDungThucHien;
import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.cntp.repository.NoiDungThucHienRepository;
import com.px.tool.domain.cntp.service.CongNhanThanhPhamService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CongNhanThanhPhamServiceImpl implements CongNhanThanhPhamService {
    @Autowired
    private CongNhanThanhPhamRepository congNhanThanhPhamRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private NoiDungThucHienRepository noiDungThucHienRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public CongNhanThanhPham saveCongNhanThanhPham(Long userId, CongNhanThanhPhamPayload congNhanThanhPhamPayload) {
        if (Objects.isNull(congNhanThanhPhamPayload.getTpId())) {
            throw new RuntimeException("Thanh Pham phai co id");
        }
        CongNhanThanhPham existedCongNhanThanhPham = congNhanThanhPhamRepository
                .findById(congNhanThanhPhamPayload.getTpId())
                .orElse(null);
//        Long requestId = existedCongNhanThanhPham.getRequest().getRequestId();
//        Long kiemHongReceiverId = existedCongNhanThanhPham.getRequest().getKiemHongReceiverId();
//        Long phieuDatHangReceiverId = existedCongNhanThanhPham.getRequest().getPhieuDatHangReceiverId();
//        Long phuongAnReceiverId = existedCongNhanThanhPham.getRequest().getPhuongAnReceiverId();
        Long cntpReceiverId = Objects.isNull(congNhanThanhPhamPayload.getNoiNhan()) ? userId : congNhanThanhPhamPayload.getNoiNhan();

        User user = userService.findById(userId);
        congNhanThanhPhamPayload.capNhatChuKy(user);
        CongNhanThanhPham congNhanThanhPham = new CongNhanThanhPham();
        congNhanThanhPhamPayload.toEntity(congNhanThanhPham);
        capNhatNgayThangNam(congNhanThanhPham, existedCongNhanThanhPham);
        cleanOldDetailData(congNhanThanhPham, existedCongNhanThanhPham);

//        requestService.updateReceiveId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
        return congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    private void capNhatNgayThangNam(CongNhanThanhPham request, CongNhanThanhPham existed) {
        if (request.getNguoiThucHienXacNhan() && !existed.getNguoiThucHienXacNhan()) {
            request.setNgayThangNamNguoiThucHien(DateTimeUtils.nowAsString());
        }
        if (request.getTpkcsXacNhan() && !existed.getTpkcsXacNhan()) {
            request.setNgayThangNamTPKCS(DateTimeUtils.nowAsString());
        }
    }

    private void cleanOldDetailData(CongNhanThanhPham requestCNTP, CongNhanThanhPham existedCongNhanThanhPham) {
        if (Objects.isNull(existedCongNhanThanhPham)) {
            return;
        }
        try {

            Set<Long> requestIds = requestCNTP.getNoiDungThucHiens()
                    .stream()
                    .map(NoiDungThucHien::getNoiDungId)
                    .collect(Collectors.toSet());

            Set<Long> ids = existedCongNhanThanhPham.getNoiDungThucHiens()
                    .stream()
                    .map(NoiDungThucHien::getNoiDungId)
                    .filter(el -> !requestIds.contains(el))
                    .collect(Collectors.toSet());

            if (!CollectionUtils.isEmpty(ids)) {
                noiDungThucHienRepository.deleteAllByIds(ids);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Co loi xay ra khi clean noi_dung_thuc_hien");
        }
    }

    @Override
    public CongNhanThanhPhamPayload timCongNhanThanhPham(Long userId, Long id) {
        Request request = requestService.findById(id);
//        CongNhanThanhPhamPayload payload = CongNhanThanhPhamPayload.fromEntity(request.getCongNhanThanhPham());

        CongNhanThanhPham existedCNTP = congNhanThanhPhamRepository
                .findById(id)
                .orElseThrow(() -> new PXException("cntp.not_found"));
        CongNhanThanhPhamPayload payload = CongNhanThanhPhamPayload.fromEntity(existedCNTP);
        payload.setRequestId(request.getRequestId());
        payload.filterPermission(userService.findById(userId));


        List<Long> signedIds = new ArrayList<>(3);
        if (payload.getTpkcsXacNhan()) {
            signedIds.add(payload.getTpkcsId());
        }
        if (payload.getNguoiThucHienXacNhan()) {
            signedIds.add(payload.getNguoiThucHienId());
        }
        if (payload.getNguoiGiaoViecXacNhan()) {
            signedIds.add(payload.getNguoiGiaoViecId());
        }
        if (org.springframework.util.CollectionUtils.isEmpty(signedIds)) {
            return payload;
        }
        for (User user : userService.findByIds(signedIds)) {
            if (user.isTruongPhongKCS() && payload.getTpkcsXacNhan()) {
                payload.setTpkcsFullName(user.getFullName());
                payload.setTpkcsSignImg(user.getSignImg());
            }
            if (user.isNguoiLapPhieu() && payload.getNguoiThucHienXacNhan()) {
                payload.setNguoiThucHienFullName(user.getFullName());
                payload.setNguoiThucHienSignImg(user.getSignImg());
            }
            if (user.isNhanVienKCS() && payload.getNguoiGiaoViecXacNhan()) {
                payload.setNguoiGiaoViecFullName(user.getFullName());
                payload.setNguoiGiaoViecSignImg(user.getSignImg());
            }
        }

        return payload;
    }

}
