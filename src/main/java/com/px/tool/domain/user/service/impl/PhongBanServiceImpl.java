package com.px.tool.domain.user.service.impl;

import com.px.tool.domain.user.PhongBan;
import com.px.tool.infrastructure.CacheService;
import com.px.tool.infrastructure.exception.PXException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PhongBanServiceImpl {

    @Autowired
    private CacheService cacheService;

    public Map<Long, PhongBan> findAll() {
        return cacheService.getPhongBanById_cache();
    }

    public PhongBan findById(Long id) {
        if (findAll().containsKey(id)) {
            return findAll().get(id);
        } else {
            throw new PXException("phuongan.not_found");
        }
    }

}
