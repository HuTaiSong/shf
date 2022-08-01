package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className HouseImageService
 * @description TODO
 * @date 2022-07-25 20:17
 */
public interface HouseImageService extends BaseService<HouseImage> {
    List<HouseImage> findList(Long houseId, int i);
}
