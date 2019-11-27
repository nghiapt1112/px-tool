package com.px.tool.domain.vanbanden.service;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.file.FileStorageService;
import com.px.tool.domain.request.payload.NoiNhan;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.domain.vanbanden.VanBanDen;
import com.px.tool.domain.vanbanden.payload.PageVanBanDenPayload;
import com.px.tool.domain.vanbanden.payload.VanBanDenDetail;
import com.px.tool.domain.vanbanden.payload.VanBanDenPageRequest;
import com.px.tool.domain.vanbanden.payload.VanBanDenRequest;
import com.px.tool.domain.vanbanden.payload.VanBanDenResponse;
import com.px.tool.domain.vanbanden.repository.VanBanDenRepository;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.service.impl.BaseServiceImpl;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VanBanDenServiceImpl extends BaseServiceImpl {
    @Autowired
    private VanBanDenRepository vanBanDenRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Những văn bản đã gửi.
     * @return
     */
    public PageVanBanDenPayload findAll(Long userId, VanBanDenPageRequest vanBanDenPageRequest) {
        Page<VanBanDen> val = vanBanDenRepository.findByCreatedBy(userId, PageRequest.of(vanBanDenPageRequest.getPage(), vanBanDenPageRequest.getSize()));
        return toResponse(val);
    }

    /**
     * Những văn bản cua toi.
     * @return
     */
    public PageVanBanDenPayload findInBox(Long userId, VanBanDenPageRequest vanBanDenPageRequest) {
        Page<VanBanDen> val = vanBanDenRepository.findByNoiNhan(userId, PageRequest.of(vanBanDenPageRequest.getPage(), vanBanDenPageRequest.getSize()));
        return toResponse(val);
    }

    private PageVanBanDenPayload toResponse(Page<VanBanDen> val) {
        PageVanBanDenPayload res = new PageVanBanDenPayload();
        if (val.isEmpty()) {
            return res;
        }
        Set<Long> ids = new HashSet<>();
        for (VanBanDen vanBanDen : val) {
            ids.add(vanBanDen.getNoiNhan());
        }
        Map<Long, String> noiNhanById = new HashMap<>();
        for (NoiNhan noiNhan : userService.findVanBanDenNoiNhan()) {
            noiNhanById.put(noiNhan.getId(), noiNhan.getName());
        }


        res.setDetails(
                val.stream()
                        .map(el -> {
                            VanBanDenResponse payload = VanBanDenResponse.fromEntity(el);
                            payload.setNoiNhan(noiNhanById.get(el.getNoiNhan()));
                            return payload;
                        })
                        .collect(Collectors.toList())
        );
        res.setTotal(val.getTotalPages());
        return res;
    }

    @Transactional
    public VanBanDenResponse save(Long userId, VanBanDenRequest payload) {
        VanBanDen entity = payload.toEntity();
        entity.setCreatedBy(userId);
        return VanBanDenResponse.fromEntity(vanBanDenRepository.save(entity));
    }

    public VanBanDenDetail findById(Long id) {
        return VanBanDenDetail
                .fromEntity(vanBanDenRepository.findById(id).orElseThrow(() -> new PXException("vanbanden_notFound")))
                .withFilesName(fileStorageService.listFileNames(RequestType.VAN_BAN_DEN, id));
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            vanBanDenRepository.deleteById(id);
        } catch (Exception e) {
            throw new PXException("vanbanden_deleteFailed");
        }
    }

    @Transactional
    public void guiVanBanDen(List<Long> group, RequestType requestType) {
        try {
            List<VanBanDen> contents = userRepository.findByGroup(group).stream()
                    .filter(el -> el.getLevel() == 3)
                    .map(el -> {
                        VanBanDen vanBanDen = new VanBanDen();
                        if (requestType == RequestType.KIEM_HONG) {
                            vanBanDen.setNoiDung("Bạn đang có một yêu cầu Kiểm Hỏng" + "ngày: " + DateTimeUtils.nowAsString());
                        } else if (requestType == RequestType.DAT_HANG) {
                            vanBanDen.setNoiDung("Bạn đang có một yêu cầu Đặt Hàng" + "ngày: " + DateTimeUtils.nowAsString());
                        } else if (requestType == RequestType.PHUONG_AN) {
                            vanBanDen.setNoiDung("Bạn đang có một yêu cầu Phương Án " + "ngày: " + DateTimeUtils.nowAsString());
                        }
                        vanBanDen.setNoiNhan(el.getUserId());
                        return vanBanDen;
                    })
                    .collect(Collectors.toList());
            vanBanDenRepository.saveAll(contents);
        } catch (Exception e) {
            logger.error("[Kiem hong] Can't save Van Ban Den");
        }
    }
}
