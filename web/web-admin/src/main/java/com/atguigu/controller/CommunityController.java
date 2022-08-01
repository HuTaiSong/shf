package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className CommunityController
 * @description TODO
 * @date 2022-07-24 16:37
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;


    @PreAuthorize("hasAuthority('community.show')")
    @RequestMapping
    public String index(HttpServletRequest request, Model model){
        //获取查询条件和分页条件acl_role_permission
        Map<String, Object> filters = super.getFilters(request);
        model.addAttribute("filters",filters);
        //根据查询条件和分页条件查询当前页的数据
        PageInfo<Community> page = this.communityService.findByPage(filters);
        model.addAttribute("page",page);
        //获取beijing的区域列表
        List<Dict> areaList = this.dictService.findListByDictCode("beijing");
        model.addAttribute("areaList",areaList);
        return "community/index";
    }

    @PreAuthorize("hasAuthority('community.create')")
    @RequestMapping("/create")
    public String  create(Model model){
        //获取beijing的区域列表
        List<Dict> areaList = this.dictService.findListByDictCode("beijing");
        model.addAttribute("areaList",areaList);
        return "community/create";
    }
    @RequestMapping("/save")
    public String save(Community community){
        this.communityService.insert(community);
        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('community.edit')")
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        //根据id查询指定的小区信息
        Community community = this.communityService.getById(id);
        model.addAttribute("community",community);
        //获取beijing的区域列表
        List<Dict> areaList = this.dictService.findListByDictCode("beijing");
        model.addAttribute("areaList",areaList);


        return "community/update";
    }
    @RequestMapping("/update")
    public String update(Community community){
        this.communityService.update(community);
        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('community.delete')")
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        this.communityService.delete(id);
        return "redirect:/community";
    }
}
