package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author nicc
 * @version 1.0
 * @className HouseUserController
 * @description TODO
 * @date 2022-07-26 00:49
 */
@Controller
@RequestMapping("/houseUser")
public class HouseUserController {
    @Reference
    private HouseUserService houseUserService;
    @RequestMapping("/create")
    public String create(Long houseId, Model model){
        model.addAttribute("houseId",houseId);
        return "houseUser/create";
    }
    @RequestMapping("/save")
    public String save(HouseUser houseUser){
        this.houseUserService.insert(houseUser);
        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('house.editUser')")
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        HouseUser houseUser = this.houseUserService.getById(id);
        model.addAttribute("houseUser",houseUser);
        return "houseUser/update";
    }
    @RequestMapping("/update")
    public String update(HouseUser houseUser){
        this.houseUserService.update(houseUser);
        return "common/successPage";
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("id") Long id, @PathVariable("houseId") Long houseId){
        this.houseUserService.delete(id);
        return "redirect:/house/detail/" + houseId;
    }
}
