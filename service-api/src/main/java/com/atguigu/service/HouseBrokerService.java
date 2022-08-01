package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className HouseBrokerService
 * @description TODO
 * @date 2022-07-25 20:16
 */
public interface HouseBrokerService extends BaseService<HouseBroker> {
    List<HouseBroker> findListByHouseId(Long houseId);
}
