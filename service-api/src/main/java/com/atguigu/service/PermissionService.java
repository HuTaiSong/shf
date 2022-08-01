package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className PermissionService
 * @description TODO
 * @date 2022-07-29 19:37
 */
public interface PermissionService extends BaseService<Permission> {

    //根据角色获取授权权限数据
    List<Map<String, Object>> findPermissionByRoleId(Long roleId);

    //保存角色权限
    void saveRolePermissionRelationship(Long roleId, Long[] permissionIds);

    //获取用户菜单权限
    List<Permission> findMenuPermissionByAdminId(Long adminId);

    List<Permission> findAllMenu();

    List<String> findCodeListByAdminId(Long adminId);
}
