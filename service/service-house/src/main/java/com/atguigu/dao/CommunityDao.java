package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className CommunityDao
 * @description TODO
 * @date 2022-07-24 16:33
 */
public interface CommunityDao extends BaseDao<Community> {

    List<Community> findAll();
}
