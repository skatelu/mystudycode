/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: InitCache
 * Author:   ZhangChangHong
 * Date:     2018/7/23
 * Description: 缓存类，预加载公参等数据到缓存中，提高查询性能
 * 先read 再write  clear和remove无需判断map为null，空map直接返回
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitCache {
    protected static Logger logger = LoggerFactory.getLogger(InitCache.class);

    protected static ReentrantLock lockCache = new ReentrantLock();

    private static Map<String, Object> mapOptCache;

    /**
     * init cache
     * @throws Exception
     */
    private static void init() throws Exception {
        lockCache.lock();
        try {
            if (mapOptCache == null) {
                mapOptCache = new ConcurrentHashMap<String, Object>();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            lockCache.unlock();
        }
    }

    /**
     * 读取缓存中数据
     * @param key
     * @return Object
     */
    public static Object read(String key) {

        if (mapOptCache == null) {
            try {
                init();
            }catch (Exception e) {
                logger.error("InitCache::read(String key) 初始化缓存出错", e);
                return null;
            }
        }

        if (!mapOptCache.isEmpty() && !"".equals(key)){
            Object obj = mapOptCache.get(key);

            if (obj != null){
                return Tools.deepClone(obj);
            }
        }else {
            logger.warn("InitCache::read(String key) 查无数据 mapOptCache:{}, key:{}", mapOptCache, key);
        }

        return null;
    }

    /**
     * 写入数据到缓存中
     * @param key
     * @param object
     * @return boolean 写入成功返回true 写入失败返回false
     */
    public static boolean write(String key, Object object) {

        if (mapOptCache == null) {
            try {
                init();
            }catch (Exception e) {
                logger.error("InitCache::write(String key, Object object) 初始化缓存出错", e);
                return false;
            }
        }

        if (!"".equals(key) && object != null){
            mapOptCache.put(key, object);
            return true;
        }else {
            logger.warn("InitCache::write(String key, Object object) 查无数据 mapOptCache:{}, key:{}, Object:{}", mapOptCache, key, object);
        }

        return false;
    }

    /**
     * 清除缓存中某一条数据
     * @param key
     * @return boolean 清除成功返回true，否则返回false
     */
    public static boolean remove(String key) {

        if (!"".equals(key) && mapOptCache != null  && !mapOptCache.isEmpty()) {
            mapOptCache.remove(key);
            return true;
        }else {
            logger.warn("InitCache::remove(String key) 查无数据 mapOptCache:{}, key:{}", mapOptCache, key);
        }

        return false;
    }

    /**
     * 清空缓存
     * @return boolean 成功返回true，否则返回false
     */
    public static boolean clean() {

        if (mapOptCache != null  && !mapOptCache.isEmpty()) {
            mapOptCache.clear();
            return true;
        }else {
            logger.warn("InitCache::clean() 缓存未初始化或为空 mapOptCache:{}", mapOptCache);
        }

        return false;
    }
}