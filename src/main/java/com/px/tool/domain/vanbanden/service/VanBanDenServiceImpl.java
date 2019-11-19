package com.px.tool.domain.vanbanden.service;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.file.FileStorageService;
import com.px.tool.domain.request.NoiNhan;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.domain.vanbanden.VanBanDen;
import com.px.tool.domain.vanbanden.VanBanDenPayload;
import com.px.tool.domain.vanbanden.repository.VanBanDenRepository;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<VanBanDenPayload> findAll(Long userId) {
        List<VanBanDen> val = vanBanDenRepository.findAll();
        Set<Long> ids = new HashSet<>();
        for (VanBanDen vanBanDen : val) {
            ids.add(vanBanDen.getNoiNhan());
        }
        Map<Long, String> noiNhanById = new HashMap<>();
        for (NoiNhan noiNhan : userService.findVanBanDenNoiNhan()) {
            noiNhanById.put(noiNhan.getId(), noiNhan.getName());
        }
        return val.stream()
                .map(el -> {
                    VanBanDenPayload payload = VanBanDenPayload.fromEntity(el);
                    payload.setNoiNhan(noiNhanById.get(el.getNoiNhan()));
                    return payload;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public VanBanDenPayload save(VanBanDenPayload payload) {
        return VanBanDenPayload.fromEntity(vanBanDenRepository.save(payload.toEntity()));
    }

    public VanBanDenPayload findById(Long id) {
        return VanBanDenPayload
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
}
