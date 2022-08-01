package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.*;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className HouseController
 * @description TODO
 * @date 2022-07-26 15:01
 */
@RestController
@RequestMapping("/house")
public class HouseController {

    @Reference
    private HouseService houseService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private UserFollowService userFollowService;

    @PostMapping(value = "/list/{pageNum}/{pageSize}")
    public Result<PageInfo<HouseVo>> findListPage(@PathVariable Integer pageNum, // 第几页
                                                  @PathVariable Integer pageSize, //每页记录数
                                                  @RequestBody HouseQueryVo houseQueryVo){ //查询条件
        //this.houseService.findPage(filters);
        PageInfo<HouseVo> pageInfo = this.houseService.findListPage(pageNum,pageSize,houseQueryVo);
        return Result.ok(pageInfo);
    }


    @RequestMapping("/info/{id}")
    public Result showInfo(@PathVariable Long id, HttpServletRequest request){

        Map map = new HashMap<>();
        //查询指定id的房源信息
        House house = this.houseService.getById(id);
        map.put("house", house);

        //查询指定房源的小区信息
        Community community = this.communityService.getById(house.getCommunityId());
        map.put("community", community);

        //查询指定房源的经纪人信息
        List<HouseBroker> houseBrokerList = this.houseBrokerService.findListByHouseId(id);
        map.put("houseBrokerList", houseBrokerList);

        //获取指定房源的图片
        List<HouseImage> houseImage1List = this.houseImageService.findList(id, 1);
        map.put("houseImage1List", houseImage1List);

        //该房源是否被关注,默认未登录情况下为false
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Boolean isFollow = false;
        if(userInfo != null){
            Long userId = userInfo.getId();
            isFollow = userFollowService.isFollow(userId, id);
        }
        map.put("isFollow", isFollow);

        //添加登录用户昵称
//        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
//        map.put("userInfo", userInfo);

        return Result.ok(map);
    }

}
