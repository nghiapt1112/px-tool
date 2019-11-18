package com.px.tool.domain.vanbanden.service;

import com.px.tool.domain.vanbanden.VanBanDenPayload;
import com.px.tool.domain.vanbanden.repository.VanBanDenRepository;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VanBanDenServiceImpl extends BaseServiceImpl {
    @Autowired
    private VanBanDenRepository vanBanDenRepository;


    public List<VanBanDenPayload> findAll() {
        return vanBanDenRepository.findAll()
                .stream()
                .map(VanBanDenPayload::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public VanBanDenPayload save(VanBanDenPayload payload) {
        return VanBanDenPayload.fromEntity(vanBanDenRepository.save(payload.toEntity()));
    }

    public VanBanDenPayload findById(Long id) {
        return VanBanDenPayload.fromEntity(
                vanBanDenRepository.findById(id)
                        .orElseThrow(() -> new PXException("vanbanden_notFound"))
        );
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
