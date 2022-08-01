package com.atguigu.base;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className BaseService
 * @description TODO
 * @date 2022-07-20 20:37
 */
public interface BaseService<T> {
    /**
     * 插入
     * @param t
     * @return
     */
    Integer insert(T t);

    /**
     * 删除
     * @param id
     */
    Integer delete(Serializable id);

    /**
     * 修改
     * @param t
     * @return
     */
    Integer update(T t);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T getById(Serializable id);


    /**
     * 分页
     * @param filters
     * @return
     */
    PageInfo<T> findByPage(Map<String, Object> filters);
}
