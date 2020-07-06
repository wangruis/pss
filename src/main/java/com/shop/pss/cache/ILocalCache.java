package com.shop.pss.cache;

/**
 * 本地缓存接口
 * @param <K> Key的类型
 * @param <V> Value的类型
 */
public interface ILocalCache <K, V> {
	
	/**
	 * 从缓存中获取数据
	 * @param key
	 * @return value
	 */
	public V get(K key);

	/**
	 *
	 */
	public Boolean invalidate(K key);
}
