package com.atguigu.base;

import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className BaseServiceImpl
 * @description TODO
 * @date 2022-07-20 20:43
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected abstract BaseDao<T> getEntityDao();

    @Override
    public Integer insert(T role) {
        return getEntityDao().insert(role);
    }

    @Override
    public T getById(Serializable id) {
        return getEntityDao().getById(id);
    }

    @Override
    public Integer update(T role) {
        //return this.roleDao.update(role);
        return getEntityDao().update(role);
    }

    @Override
    public Integer delete(Serializable id) {
        return getEntityDao().delete(id);
    }
    /*
     * 查询条件 roleName roleCode
     * 分页条件：pageNum，pageSize
     * roleName:超级管理员
     * pageNum:2
     * pageSize:5
     */
    @Override
    public PageInfo<T> findByPage(Map<String, Object> filters) {
        int pageNum =  CastUtil.castInt(filters.get("pageNum"),1);
        int pageSize =  CastUtil.castInt(filters.get("pageSize"),5);
        //启用分页插件:
        // select count(*)....获取符号查询条件的记录总数
        //给查询条件添加limit 子句
        PageHelper.startPage(pageNum,pageSize);
        //查询当前页的数据
        Page<T> page =  getEntityDao().findByPage(filters);
        //封装数据并返回
        return new PageInfo<>(page,10);
    }
}
