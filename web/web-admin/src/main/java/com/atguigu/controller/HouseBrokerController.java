package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className HouseBrokerController
 * @description TODO
 * @date 2022-07-25 21:06
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {

    @Reference
    private AdminService adminService;
    @Reference
    private HouseBrokerService houseBrokerService;


    @RequestMapping("/create")
    public String create(Long houseId,Model model){
        //携带所有的Admin列表
        List<Admin> adminList = this.adminService.findAll();
        model.addAttribute("adminList",adminList);

        model.addAttribute("houseId",houseId);
        return "houseBroker/create";
    }
    @RequestMapping("/save")
    public String save(Long houseId,Long brokerId){
        Admin admin = this.adminService.getById(brokerId);

        HouseBroker houseBroker = new HouseBroker();
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerId(brokerId);
        houseBroker.setHouseId(houseId);
        this.houseBrokerService.insert(houseBroker);
        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('house.editBroker')")
    @RequestMapping("/edit/{id}") //经纪人ID
    public String edit(@PathVariable Long id, Model model){
        //携带所有的Admin列表
        List<Admin> adminList = this.adminService.findAll();
        model.addAttribute("adminList",adminList);
        //按照id查询经纪人
        HouseBroker houseBroker = this.houseBrokerService.getById(id);
        model.addAttribute("houseBroker",houseBroker);
        return "houseBroker/update";
    }
    @RequestMapping("/update")
    public String  update(HouseBroker houseBroker){ //id  brokerId
        // 获取指定的Admin的信息
        Admin admin = this.adminService.getById(houseBroker.getBrokerId());
        //houseBroker.setHouseId(); 和当前操作无关
        //houseBroker.setBrokerId(); 已经存在
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        //houseBroker.setId(); 已经存在
        this.houseBrokerService.update(houseBroker);
        return "common/successPage";
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable Long id){
        houseBrokerService.delete(id);
        return "redirect:/house/detail/" + houseId;
    }
}
