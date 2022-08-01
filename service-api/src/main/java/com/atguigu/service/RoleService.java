package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className RoleService
 * @description TODO
 * @date 2022-07-19 14:15
 */
public interface RoleService extends BaseService<Role> {

    List<Role> findAll();

    Map<String, Object> findRoleByAdminId(Long adminId);

    void saveUserRelationShip(Long adminId, Long[] roleIds);
}
