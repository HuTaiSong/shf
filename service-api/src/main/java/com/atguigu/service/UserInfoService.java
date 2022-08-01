package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserInfo;

/**
 * @author nicc
 * @version 1.0
 * @className UserInfoService
 * @description TODO
 * @date 2022-07-27 20:49
 */
public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getByPhone(String phone);
}
