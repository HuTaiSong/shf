package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className PermissionDao
 * @description TODO
 * @date 2022-07-29 19:41
 */
public interface PermissionDao extends BaseDao<Permission> {

    List<Permission> findAll();

    List<Permission> findMenuPermissionByAdminId(Long adminId);

    List<String> findCodeListByAdminId(Long adminId);

    List<String> findAllCodeList();
}
