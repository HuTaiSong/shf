package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

/**
 * @author nicc
 * @version 1.0
 * @className UserFollowService
 * @description TODO
 * @date 2022-07-28 11:44
 */
public interface UserFollowService extends BaseService<UserFollow>{
    void follow(Long userId, Long houseId);

    PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId);

    Boolean isFollow(Long userId, Long houseId);
}
