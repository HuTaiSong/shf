package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminDao;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className AdminServiceImpl
 * @description TODO
 * @date 2022-07-21 11:05
 */
//dubbo提供的@Service注解,并配置事务
@Service(interfaceClass = AdminService.class)
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    protected BaseDao<Admin> getEntityDao() {
        return adminDao;
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    @Override
    public Admin getByUsername(String username) {
        return adminDao.getByUsername(username);
    }
}
