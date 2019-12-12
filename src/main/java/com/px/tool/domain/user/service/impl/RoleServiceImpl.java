package com.px.tool.domain.user.service.impl;

import com.px.tool.domain.user.Role;
import com.px.tool.domain.user.repository.RoleRepository;
import com.px.tool.infrastructure.CacheService;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.logger.PXLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl {

    @Autowired
    private RoleRepository roleRepository;

    //    @Cacheable(value = "reservationsCache", key = "#userName")
    @Cacheable(value = CacheService.CACHE_ROLE)
    public Map<Long, Role> findAll() {
        PXLogger.info("Finding log...");
        return roleRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Role::getRoleId, o -> o));
    }

    public Role findById(Long id) {
        if (this.findAll().containsKey(id)) {
            return this.findAll().get(id);
        } else {
            throw new PXException("role.not_found");
        }
    }
}
