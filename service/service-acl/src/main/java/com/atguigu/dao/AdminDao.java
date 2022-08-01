package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className AdminDao
 * @description TODO
 * @date 2022-07-21 11:06
 */
public interface AdminDao extends BaseDao<Admin> {

    List<Admin> findAll();

    Admin getByUsername(String username);
}
