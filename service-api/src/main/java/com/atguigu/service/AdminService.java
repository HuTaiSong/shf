package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className AdminService
 * @description TODO
 * @date 2022-07-21 11:04
 */
public interface AdminService extends BaseService<Admin> {

    List<Admin> findAll();

    Admin getByUsername(String username);
}
