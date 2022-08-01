package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className HouseBrokerBao
 * @description TODO
 * @date 2022-07-25 20:21
 */
public interface HouseBrokerDao extends BaseDao<HouseBroker> {

    List<HouseBroker> findListByHouseId(Long houseId);
}

