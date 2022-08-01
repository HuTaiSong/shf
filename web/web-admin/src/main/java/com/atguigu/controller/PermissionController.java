package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Permission;
import com.atguigu.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className PressionController
 * @description TODO
 * @date 2022-07-30 00:43
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @PreAuthorize("hasAuthority('permission.show')")
    @RequestMapping
    public String index(Model model){
        List<Permission> list = permissionService.findAllMenu();
        model.addAttribute("list", list);

        return "permission/index";
    }

    @PreAuthorize("hasAuthority('permission.create')")
    @RequestMapping("/create")
    public String create(Model model, Permission permission){
        model.addAttribute("permission", permission);
        return "permission/create";
    }

    @RequestMapping("/save")
    public String save(Permission permission){
        permissionService.insert(permission);

        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('permission.edit')")
    @RequestMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable Long id) {
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission",permission);
        return "permission/update";
    }

    @RequestMapping("/update")
    public String update(Permission permission) {
        permissionService.update(permission);
        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('permission.delete')")
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        permissionService.delete(id);
        return "redirect:/permission";
    }

}
