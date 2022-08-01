package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author nicc
 * @version 1.0
 * @className UserFollowController
 * @description TODO
 * @date 2022-07-28 11:41
 */

@RestController
@RequestMapping("/userFollow")
public class UserFollowController extends BaseController {

    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable Long houseId, HttpServletRequest request){
        //从session域中获取userInfo
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");

        //获取登陆用户的id
        Long userId = userInfo.getId();

        //调用service层方法
        userFollowService.follow(userId, houseId);

        return Result.ok();
    }
    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result<PageInfo<UserFollowVo>> list(@PathVariable Integer pageNum, @PathVariable Integer pageSize, HttpServletRequest request){
        //获取当前用户id
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();

        PageInfo<UserFollowVo> page = this.userFollowService.findListPage(pageNum, pageSize, userId);

        return Result.ok(page);
    }
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable Long id){

        this.userFollowService.delete(id);
        return Result.ok();
    }

}
