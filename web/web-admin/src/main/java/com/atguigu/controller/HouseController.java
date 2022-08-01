package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.service.*;
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
 * @className HouseController
 * @description TODO
 * @date 2022-07-25 18:42
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    private HouseService houseService;
    @Reference
    private DictService dictService;
    @Reference
    private CommunityService communityService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseUserService houseUserService;


    @PreAuthorize("hasAuthority('house.show')")
    @RequestMapping
    public String findByPage(Model model, HttpServletRequest request){
        //获取分页信息
        Map<String, Object> filters = getFilters(request);
        model.addAttribute("filters", filters);

        //获取分页查询结果
        PageInfo<House> page = houseService.findByPage(filters);
        model.addAttribute("page", page);

        //获取小区信息 communityList
        List<Community> communityList = communityService.findAll();
        model.addAttribute("communityList", communityList);

        //获取户型信息 houseTypeList
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        model.addAttribute("houseTypeList", houseTypeList);

        //获取楼层信息 floorList
        List<Dict> floorList = dictService.findListByDictCode("floor");
        model.addAttribute("floorList", floorList);

        //获取建筑结构信息 buildStructureList
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        model.addAttribute("buildStructureList", buildStructureList);

        //获取朝向信息 directionList
        List<Dict> directionList = dictService.findListByDictCode("direction");
        model.addAttribute("directionList", directionList);

        //获取装修信息 decorationList
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        model.addAttribute("decorationList", decorationList);

        //获取房屋用途信息 houseUseList
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        model.addAttribute("houseUseList", houseUseList);

        return "house/index";
    }

    @PreAuthorize("hasAuthority('house.create')")
    @RequestMapping("/create")
    public String create(Model model){
        //获取小区信息 communityList
        List<Community> communityList = communityService.findAll();
        model.addAttribute("communityList", communityList);

        //查询6个字典项列表
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        model.addAttribute("houseTypeList", houseTypeList);
        List<Dict> floorList = dictService.findListByDictCode("floor");
        model.addAttribute("floorList", floorList);
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        model.addAttribute("buildStructureList", buildStructureList);
        List<Dict> directionList = dictService.findListByDictCode("direction");
        model.addAttribute("directionList", directionList);
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        model.addAttribute("decorationList", decorationList);
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        model.addAttribute("houseUseList", houseUseList);

        return "house/create";
    }

    @RequestMapping("/save")
    public String save(House house){
        houseService.insert(house);
        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('house.edit')")
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        //根据id查询房源信息
        House house = this.houseService.getById(id);
        model.addAttribute("house",house);

        //查询小区列表
        List<Community> communityList =  this.communityService.findAll();
        model.addAttribute("communityList",communityList);

        //查询6个字典项列表
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        model.addAttribute("houseTypeList", houseTypeList);
        List<Dict> floorList = dictService.findListByDictCode("floor");
        model.addAttribute("floorList", floorList);
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        model.addAttribute("buildStructureList", buildStructureList);
        List<Dict> directionList = dictService.findListByDictCode("direction");
        model.addAttribute("directionList", directionList);
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        model.addAttribute("decorationList", decorationList);
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        model.addAttribute("houseUseList", houseUseList);

        return "house/update";
    }

    @RequestMapping("/update")
    public String update(House house){
        this.houseService.update(house);
        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('house.delete')")
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        this.houseService.delete(id);
        return "redirect:/house";
    }

    /**
     * 发布/取消发布
     * @param id
     * @param status
     * @return
     */
    @PreAuthorize("hasAuthority('house.publish')")
    @RequestMapping("/publish/{houseId}/{status}")
    public String publish(@PathVariable("houseId") Long id,@PathVariable Integer status){
        this.houseService.publish(id,status);
        return "redirect:/house";
    }

    @RequestMapping("/detail/{houseId}")
    public String show(@PathVariable Long houseId, Model model){
        //  1、房源信息
        House house = this.houseService.getById(houseId);
        model.addAttribute("house",house);
        //  2、小区信息
        Community community = this.communityService.getById(house.getCommunityId());
        model.addAttribute("community",community);
        // 	3、房源图片信息（前台展示的），对应表：hse_house_image（type=1）
        List<HouseImage> houseImage1List = houseImageService.findList(houseId, 1);
        model.addAttribute("houseImage1List",houseImage1List);
        // 	4、房产图片信息（后台经纪人收集的信息，不对外发布），对应表：hse_house_image（type=2）
        List<HouseImage> houseImage2List = houseImageService.findList(houseId, 2);
        model.addAttribute("houseImage2List",houseImage2List);
        // 	5、经纪人信息，对应表：hse_house_broker
        List<HouseBroker> listByHouseId = this.houseBrokerService.findListByHouseId(houseId);
        model.addAttribute("houseBrokerList",listByHouseId);

        // 	6、房东信息，对应表：hse_house_user
        List<HouseUser> houseUserList = this.houseUserService.findListByHouseId(houseId);
        model.addAttribute("houseUserList",houseUserList);

        return "house/show";
    }


}
