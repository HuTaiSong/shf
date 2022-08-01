package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.service.DictService;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className UserFollowServiceImpl
 * @description TODO
 * @date 2022-07-28 11:44
 */
@Service(interfaceClass = UserFollowService.class)
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowDao userFollowDao;

    @Reference
    private DictService dictService;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long userId, Long houseId) {
        //将参数封装进UserFollow对象实例
        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(userId);
        userFollow.setHouseId(houseId);
        userFollowDao.insert(userFollow);
    }

    @Override
    public PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId) {
        //开启分页插件
        PageHelper.startPage(pageNum, pageSize);

        //查询本页数据
        Page<UserFollowVo> page =  this.userFollowDao.findListPage(userId);

        //根据id获取name
        List<UserFollowVo> list = page.getResult();
        for(UserFollowVo userFollowVo : list){
            //houseTypeId---------houseTypeName
            String houseTypeName = this.dictService.getNameById(userFollowVo.getHouseTypeId());
            userFollowVo.setHouseTypeName(houseTypeName);
            //floorId-----floorName
            String floorName = this.dictService.getNameById(userFollowVo.getFloorId());
            userFollowVo.setFloorName(floorName);
            //directionId----directionName
            String directionName = this.dictService.getNameById(userFollowVo.getDirectionId());
            userFollowVo.setDirectionName(directionName);
        }
        return new PageInfo<>(page, 5);
    }

    @Override
    public Boolean isFollow(Long userId, Long houseId) {
        Integer count = userFollowDao.countByUserIdAndHouseId(userId, houseId);

        if(count.intValue() == 0){
            return false;
        }

        return true;
    }
}
