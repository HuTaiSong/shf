package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.UserInfo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import com.atguigu.util.ValidateCodeUtils;
import com.atguigu.vo.LoginVo;
import com.atguigu.vo.RegisterVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className UserInfoController
 * @description TODO
 * @date 2022-07-28 09:30
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {

    @Reference
    private UserInfoService userInfoService;

    //发送验证码
    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable String phone, HttpServletRequest request){

        //产生一个随机的验证码
        String code = ValidateCodeUtils.generateValidateCode4String(6);
        System.out.println(code);
        //将验证码放入session，注册是判断验证码是否正确
        request.getSession().setAttribute("code",code);
        ////使用阿里短信等发出去
        //返回验证码
        return Result.ok(code);
    }

    //注册
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request){
        //获取四个表单项的值
        String nickName = registerVo.getNickName();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        if(StringUtils.isEmpty(nickName)
                ||StringUtils.isEmpty(phone)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code)){
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        //判断验证码是否正确
        String codeStr = (String)request.getSession().getAttribute("code");
        if(StringUtils.isEmpty(codeStr) || !codeStr.equals(code)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }
        //判断手机号码是否已经被占用
        UserInfo userInfo = this.userInfoService.getByPhone(phone);
        if(userInfo !=null ){
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        //实现用户的注册
        userInfo = new UserInfo();
        userInfo.setStatus(1);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setPhone(phone);
        userInfo.setNickName(nickName);
        this.userInfoService.insert(userInfo);

        return Result.ok();
    }

    //登录
    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request){
        //获取登录信息
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        //判断是否为空
        if(StringUtils.isEmpty(phone) ||
            StringUtils.isEmpty(password)){
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        //按照phone进行查询
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(userInfo == null){
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }

        //判断密码是否正确:密文的比较
        if(!userInfo.getPassword().equals(MD5.encrypt(password))){
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }

        //判断用户是否被禁用status
        if(userInfo.getStatus() == 0){
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }

        //一切正常，返回ok，要携带用户的昵称过去
        request.getSession().setAttribute("USER", userInfo);
        Map map = new HashMap<>();
        map.put("nickName", userInfo.getNickName());

        return Result.ok(map);
    }

    //登出
    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request){
        //结束当前的session
        request.getSession().invalidate(); //结束session，开启一个新的session
        //request.getSession().removeAttribute("USER");//session不变，只是清除其中的指定数据
        return Result.ok();
    }
}
