package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
//@RequestMapping("/index")
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;


    @RequestMapping
    public String index(Model model){
        //Long adminId = 1L;  //超级管理员
        //Long adminId = 10L; //普通用户

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication == null){
//            return "frame/index";
//        }
        User user = (User)authentication.getPrincipal();
        String username = user.getUsername();
        Admin admin = adminService.getByUsername(username);

        //Admin admin = adminService.getById(adminId);
        model.addAttribute("admin",admin);
        List<Permission> permissionList = this.permissionService.findMenuPermissionByAdminId(admin.getId());
        model.addAttribute("permissionList",permissionList);
        return "frame/index";
    }

    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }

    @RequestMapping("/loginPage")
    public String loginPage(){
        return "frame/login";
    }

    @RequestMapping("/getInfo")
    public Object getInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getPrincipal();
    }

    @GetMapping("/auth")
    public String auth() {
        return "common/auth";
    }

//    @RequestMapping
//    public String logout(){
//        return "";
//    }
}
