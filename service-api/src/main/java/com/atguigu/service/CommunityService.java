package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className CommunityService
 * @description TODO
 * @date 2022-07-23 14:11
 */
public interface CommunityService extends BaseService<Community> {
    public List<Community> findAll();
}
