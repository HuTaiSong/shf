package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

//    @RequestMapping
//    public String findAll(Model model){
//        List<Role> roleList = this.roleService.findAll();
//        model.addAttribute("roleList",roleList);//!!!
//        return "role/index";
//    }

    @PreAuthorize("hasAuthority('role.show')")  //方法调用前进行权限判断
    @RequestMapping
    public String findPage(Model model,HttpServletRequest request){
        //获取查询条件(roleName)和分页条件(pageNum,pageSize)
        Map<String,Object> filters =getFilters(request);
        //访问业务层完成分页查询
        PageInfo<Role> page = this.roleService.findByPage(filters);
        model.addAttribute("page",page);//分页数据：当前页数据，分页条的参数
        model.addAttribute("filters",filters);//查询条件和分页的两个参数（pageNum，pageSize）
        return "role/index";
    }


    @PreAuthorize("hasAuthority('role.create')")
    @RequestMapping("/create")
    public String create(){ //尝试改为视图控制器 view-controller
        return "role/create";
    }

    @RequestMapping("/save")
    public String save(Role role ){
        this.roleService.insert(role);
        //return "forward:/role"; //WEB-INF/templates/index.html
        //return "redirect:/role"; //添加、修改完毕后建议使用重定向，避免表单的重复提交
        return "common/successPage.html"; //.html.html
    }

    @PreAuthorize("hasAuthority('role.edit')")
    @RequestMapping("/edit/{idd}")
    public String edit111(@PathVariable("idd") Long id,Model  model){
        //查询指定id的数据
        Role role = this.roleService.getById(id);
        model.addAttribute("role",role);
        //页面跳转
        return "role/update";

    }

    @RequestMapping("/update")
    public String update(Role role){
        this.roleService.update(role);
        return "common/successPage";
    }


    @PreAuthorize("hasAuthority('role.delete')")
    @RequestMapping("/delete")
    public String delete(Long id){
        this.roleService.delete(id);
        return "redirect:/role";
    }

    @RequestMapping("/assignShow/{roleId}")
    public String assignShow(@PathVariable Long roleId, Model model){
        model.addAttribute("roleId",roleId);

        //获取该角色权限信息
        List<Map<String, Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        model.addAttribute("zNodes", zNodes);
        return "role/assignShow";
    }

    @PreAuthorize("hasAuthority('role.assign')")
    @RequestMapping("/assignPermission")
    public String assignPermission(Long roleId, Long[] permissionIds){
        this.permissionService.saveRolePermissionRelationship(roleId, permissionIds);
        return "common/successPage";
    }



}
