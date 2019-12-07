package com.px.tool.domain.user.service.impl;

import com.px.tool.domain.user.PhongBan;
import com.px.tool.domain.user.repository.PhongBanRepository;
import com.px.tool.infrastructure.CacheConfiguration;
import com.px.tool.infrastructure.exception.PXException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PhongBanServiceImpl {

    @Autowired
    private PhongBanRepository phongBanRepository;

    @Cacheable(value = CacheConfiguration.CACHE_PHONG_BAN)
    public Map<Long, PhongBan> findAll() {
        return phongBanRepository.findAll()
                .stream()
                .collect(Collectors.toMap(PhongBan::getPhongBanId, o -> o));
    }

    public PhongBan findById(Long id) {
        if (findAll().containsKey(id)) {
            return findAll().get(id);
        } else {
            throw new PXException("phuongan.not_found");
        }
    }

}
