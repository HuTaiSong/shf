package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;

/**
 * @author nicc
 * @version 1.0
 * @className HouseService
 * @description TODO
 * @date 2022-07-25 18:37
 */
public interface HouseService extends BaseService<House> {

    void publish(Long id, Integer status);

    PageInfo<HouseVo> findListPage(int pageNum, int pageSize, HouseQueryVo houseQueryVo);

}
