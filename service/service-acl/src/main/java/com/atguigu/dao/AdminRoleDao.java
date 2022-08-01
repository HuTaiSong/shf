package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.AdminRole;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className AdminRoleDao
 * @description TODO
 * @date 2022-07-29 16:17
 */
public interface AdminRoleDao extends BaseDao<AdminRole> {

    List<Long> findRoleIdsByAdminId(Long adminId);

    void deleteByAdminId(Long adminId);
}
