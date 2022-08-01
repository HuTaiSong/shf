package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.RolePermission;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className RolePermissionDao
 * @description TODO
 * @date 2022-07-29 19:50
 */
public interface RolePermissionDao extends BaseDao<RolePermission> {

    List<Long> findPermissionIdsByRoleId(Long roleId);

    void deletePermissionsByRoleId(Long roleId);
}

