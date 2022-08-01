package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.House;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;

/**
 * @author nicc
 * @version 1.0
 * @className HouseDao
 * @description TODO
 * @date 2022-07-25 18:37
 */
public interface HouseDao extends BaseDao<House> {

    Page<HouseVo> findListPage(HouseQueryVo houseQueryVo);
}
