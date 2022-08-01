package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Role;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className RoleDao
 * @description TODO
 * @date 2022-07-19 11:21
 */
public interface RoleDao extends BaseDao<Role> {

    List<Role> findAll();

}
