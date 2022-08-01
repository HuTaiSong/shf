package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author nicc
 * @version 1.0
 * @className UserFollowDao
 * @description TODO
 * @date 2022-07-28 11:42
 */

public interface UserFollowDao extends BaseDao<UserFollow> {

    Page<UserFollowVo> findListPage(Long userId);

    Integer countByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId")Long houseId);
}
