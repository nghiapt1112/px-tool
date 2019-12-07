package com.px.tool.infrastructure;

import com.px.tool.infrastructure.logger.PXLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheConfiguration {

    public static final String CACHE_ROLE = "roles_cache";
    public static final String CACHE_PHONG_BAN = "phong_ban_cache";
    public static final String CACHE_USER = "users_cache";
    @Autowired
    private CacheManager cacheManager;

    /**
     * Automatically clear the cache for each 120(sec).
     */
    @Scheduled(fixedRate = 120_000)
    public void evictAllCacheValues() {
        PXLogger.info("Preparing to clear caches!!!");
        cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        PXLogger.info("Caches are now empty.");
    }

}
