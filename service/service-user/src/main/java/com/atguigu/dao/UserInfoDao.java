package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.UserInfo;

/**
 * @author nicc
 * @version 1.0
 * @className UserInfoDao
 * @description TODO
 * @date 2022-07-27 10:11
 */
public interface UserInfoDao extends BaseDao<UserInfo> {

    UserInfo getByPhone(String phone);
}
