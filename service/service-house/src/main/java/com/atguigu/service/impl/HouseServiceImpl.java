package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.dao.HouseDao;
import com.atguigu.entity.House;
import com.atguigu.service.HouseService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className HouseServiceImpl
 * @description TODO
 * @date 2022-07-25 18:38
 */

@Service(interfaceClass = HouseService.class)
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long id, Integer status) {
        //查询指定id的房源信息（存储的是id不是name）
        House house = super.getById(id);

        house.setStatus(status);

        houseDao.update(house);
    }


    @Override
    public House getById(Serializable id) {
        //查询指定id的房源信息（存储的是id不是name）
        House house = super.getById(id);

        //根据id获取name
        String houseTypeName = this.dictDao.getNameById(house.getHouseTypeId());
        house.setHouseTypeName(houseTypeName);

        //根据id获取楼层信息
        String floorName = this.dictDao.getNameById(house.getFloorId());
        house.setFloorName(floorName);

        //根据id获取建筑结构信息
        String buildStructureName = this.dictDao.getNameById(house.getBuildStructureId());
        house.setBuildStructureName(buildStructureName);

        //根据id获取朝向信息
        String directionName = this.dictDao.getNameById(house.getDirectionId());
        house.setDirectionName(directionName);

        //根据id获取装修信息
        String decorationName = this.dictDao.getNameById(house.getDecorationId());
        house.setDecorationName(decorationName);

        //根据id获取房东名字
        String houseUseName = this.dictDao.getNameById(house.getHouseUseId());
        house.setHouseUseName(houseUseName);

        //返回房源信息
        return house;
    }

    @Override
    public PageInfo<HouseVo> findListPage(int pageNum, int pageSize, HouseQueryVo houseQueryVo) {
        //启用分页插件
        PageHelper.startPage(pageNum,pageSize);
        //查询指定页的数据
        Page<HouseVo> page = this.houseDao.findListPage(houseQueryVo);
        //根据字典id或者字典name
        List<HouseVo> houseVoList = page.getResult();
        for(HouseVo houseVo:houseVoList){
            String directionName = this.dictDao.getNameById(houseVo.getDirectionId());
            houseVo.setDirectionName(directionName);
            String floorName = this.dictDao.getNameById(houseVo.getFloorId());
            houseVo.setFloorName(floorName);
            String houseTypeName = this.dictDao.getNameById(houseVo.getHouseTypeId());
            houseVo.setHouseTypeName(houseTypeName);

        }
        //返回数据
        return new PageInfo<>(page);
    }

}
