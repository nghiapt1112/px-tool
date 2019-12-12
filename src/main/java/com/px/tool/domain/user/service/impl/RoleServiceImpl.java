package com.px.tool.domain.user.service.impl;

import com.px.tool.domain.user.Role;
import com.px.tool.infrastructure.CacheService;
import com.px.tool.infrastructure.exception.PXException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RoleServiceImpl {

    @Autowired
    private CacheService cacheService;

    public Map<Long, Role> findAll() {
        return cacheService.getRoleById_cache();
    }

    public Role findById(Long id) {
        if (this.findAll().containsKey(id)) {
            return this.findAll().get(id);
        } else {
            throw new PXException("role.not_found");
        }
    }
}
