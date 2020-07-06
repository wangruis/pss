package com.shop.pss.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * @author 王瑞
 * @description Guava cache 本地缓存
 * @date 2018-12-17 17:27
 */
public class LocalCache {

    private final static Logger LOGGER = LoggerFactory.getLogger(LocalCache.class);
    private final static long MAXIMUMSIZE = 100;

    private static volatile LoadingCache<String, Map> cache;

    public LoadingCache<String, Map> getCache() {
        if (cache == null) {
            synchronized (LoadingCache.class) {
                cache = CacheBuilder
                        .newBuilder()
                        .maximumSize(MAXIMUMSIZE)
                        .expireAfterAccess(3000, TimeUnit.MINUTES)
                        .build(new CacheLoader<String, Map>() {
                            @Override
                            public Map load(String key) throws Exception {
                                return loadKey(key);
                            }
                        });
                LOGGER.info("初始化");
            }
        }
        return cache;
    }


    public Map<String, Set<String>> loadKey(String key) {
        Map<String, Set<String>> map = new HashMap<>();
        return map;
    }

    public Map<String, Set<String>> get(String key) throws ExecutionException {
        Map<String, Set<String>> map = new HashMap<>();

        map = (Map<String, Set<String>>) getCache().get(key);
        return map;
    }


}
