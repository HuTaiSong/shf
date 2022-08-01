package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className HouseUserService
 * @description TODO
 * @date 2022-07-25 20:16
 */
public interface HouseUserService extends BaseService<HouseUser> {
    List<HouseUser> findListByHouseId(Long houseId);
}
