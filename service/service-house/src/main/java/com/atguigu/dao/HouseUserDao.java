package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className HouseUserDao
 * @description TODO
 * @date 2022-07-25 20:20
 */
public interface HouseUserDao extends BaseDao<HouseUser> {

    List<HouseUser> findListByHouseId(Long houseId);
}
