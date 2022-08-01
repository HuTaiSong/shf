package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import com.atguigu.util.QiniuUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Reference
    private AdminService adminService;
    @Reference
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PreAuthorize("hasAuthority('admin.show')")
    @RequestMapping
    public String findPage(HttpServletRequest request, Model model){
        //获取查询条件和分页参数
        Map<String, Object> filters = super.getFilters(request);
        //调用业务层查询分页数据
        PageInfo<Admin> page = this.adminService.findByPage(filters);
        model.addAttribute("page",page);
        model.addAttribute("filters",filters);
        //页面跳转
        return "admin/index";
    }

    @PreAuthorize("hasAuthority('admin.create')")
    @RequestMapping("/create")
    public String create(){
        return "admin/create";
    }

    @RequestMapping("/save")
    public String save(Admin admin){
        //密码加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        admin.setHeadUrl("http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg");
        this.adminService.insert(admin);
        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('admin.edit')")
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        Admin admin = this.adminService.getById(id);
        model.addAttribute("admin",admin);
        return "admin/update";
    }

    @RequestMapping("/update")
    public String update(Admin admin){
        //admin.setHeadUrl("http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg");
        this.adminService.update(admin);
        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('admin.delete')")
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        this.adminService.delete(id);
        return "redirect:/admin";
    }

    //跳转到图像上传页面
    @RequestMapping("/uploadShow/{id}")
    public String uploadShow(@PathVariable Long id, Model model){
        model.addAttribute("id", id);
        return "admin/upload";
    }

    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable Long id, MultipartFile file){
        //上传图片到七牛云
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+extName;
        try {
            QiniuUtil.upload2Qiniu(file.getBytes(), fileName);
            //修改数据库中Admin的头像地址
            Admin admin = new Admin();
            admin.setId(id);
            admin.setHeadUrl("http://rfmijz5u2.hb-bkt.clouddn.com/"+fileName);
            adminService.update(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return "common/successPage";
        }

    }

    @PreAuthorize("hasAuthority('admin.assign')")
    //进入分配角色页面
    @RequestMapping("/assignShow/{adminId}")
    public String assignSHow(@PathVariable Long adminId , Model model){

        model.addAttribute("adminId", adminId);

        //调用roleService层的findRoleByAdminId方法从
        Map<String, Object> roleMap = roleService.findRoleByAdminId(adminId);
        model.addAllAttributes(roleMap);

        return "admin/assignShow";
    }

    //根据用户分配角色
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId, Long[] roleIds){
        roleService.saveUserRelationShip(adminId, roleIds);
        return "common/successPage";
    }

}
