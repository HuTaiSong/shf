package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className HouseImageDao
 * @description TODO
 * @date 2022-07-25 20:19
 */
public interface HouseImageDao extends BaseDao<HouseImage> {

    List<HouseImage> findList(@Param("houseId")Long houseId, @Param("type")int type);
}
