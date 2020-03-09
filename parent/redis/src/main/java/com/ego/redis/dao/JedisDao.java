package com.ego.redis.dao;

public interface JedisDao {
	
	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	Boolean exists(String key);
	
	/**
	 * 删除key
	 * @param key
	 * @return
	 */
	Long del(String key);
	
	/**
	 * 添加值
	 * @param key
	 * @param value
	 * @return
	 */
	String set(String key,String value);
	
	/**
	 * 取值
	 * @param value
	 * @return
	 */
	String get(String key);
}
