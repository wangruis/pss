package com.shop.pss.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * 抽象Guava缓存类、缓存模板。
 * 子类需要实现fetchData(key)，从数据库或其他数据源（如Redis）中获取数据。
 * 子类调用getValue(key)方法，从缓存中获取数据，并处理不同的异常，比如value为null时的InvalidCacheLoadException异常。
 *
 * @param <K> key 类型
 * @param <V> value 类型
 */
public abstract class   GuavaAbstractLoadingCache<K, V> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int expireAfterWriteDuration = 60; //数据存在时长，子类在构造方法中调用setExpireAfterWriteDuration(int duration)来更改

    //用于初始化cache的参数及其缺省值
    private int maximumSize = 1000;                    //最大缓存条数，子类在构造方法中调用setMaximumSize(int size)来更改

    private TimeUnit timeUnit = TimeUnit.MINUTES;    //时间单位（分钟）

    private LoadingCache<K, V> cache;

    /**
     * 通过调用getCache().get(key)来获取数据
     *
     * @return cache
     */
    //@PostConstruct 也可以初始化时创建cache
    public LoadingCache<K, V> getCache() {
        if (cache == null) {    //使用双重校验锁保证只有一个cache实例
            synchronized (this) {
                cache = CacheBuilder.newBuilder().maximumSize(maximumSize)        //缓存数据的最大条目，也可以使用.maximumWeight(weight)代替
                        .expireAfterWrite(expireAfterWriteDuration, timeUnit)    //数据被创建多久后被移除
                        .recordStats()                                            //启用统计
                        .build(new CacheLoader<K, V>() {
                            @Override
                            public V load(K key) throws Exception {
                                return fetchData(key);
                            }
                        });
                logger.debug("本地缓存{}初始化成功", this.getClass().getSimpleName());
            }
        }

        return cache;
    }

    /**
     * 根据key从数据库或其他数据源中获取一个value，并被自动保存到缓存中。
     *
     * @param key
     * @return value, 连同key一起被加载到缓存中的。
     */
    protected abstract V fetchData(K key);

    /**
     * 从缓存中获取数据（第一次自动调用fetchData从外部获取数据），并处理异常
     *
     * @param key
     * @return Value
     * @throws ExecutionException
     */
    protected V getValue(K key) throws ExecutionException {
        V result = getCache().get(key);

        return result;
    }

    public int getMaximumSize() {
        return maximumSize;
    }

    public int getExpireAfterWriteDuration() {
        return expireAfterWriteDuration;
    }

    /**
     * 设置最大缓存条数
     *
     * @param maximumSize
     */
    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    /**
     * 设置数据存在时长（分钟）
     *
     * @param expireAfterWriteDuration
     */
    public void setExpireAfterWriteDuration(int expireAfterWriteDuration) {
        this.expireAfterWriteDuration = expireAfterWriteDuration;
    }

    protected Boolean invalidateValue(K key) throws ExecutionException {
        getCache().invalidate(key);
        return true;
    }

}
