package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.CommunityDao;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Community;
import com.atguigu.service.CommunityService;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className CommunityServiceImpl
 * @description TODO
 * @date 2022-07-24 16:31
 */

@Service(interfaceClass = CommunityService.class)
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {
    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public PageInfo<Community> findByPage(Map<String, Object> filters) {
        int pageNum =  CastUtil.castInt(filters.get("pageNum"),1);
        int pageSize =  CastUtil.castInt(filters.get("pageSize"),5);
        //启用分页插件:
        PageHelper.startPage(pageNum,pageSize);
        //查询当前页的数据
        Page<Community> page =  getEntityDao().findByPage(filters);
        //根据areaId和plateId获取areaName和plateName
        List<Community> communityList = page.getResult();
        for(Community community:communityList){
            String areaName = this.dictDao.getNameById(community.getAreaId());
            community.setAreaName(areaName);
            String plateName = this.dictDao.getNameById(community.getPlateId());
            community.setPlateName(plateName);
        }
        //封装数据并返回
        return new PageInfo<>(page,10);
    }

    @Override
    public Community getById(Serializable id) {
        //有字典id没有字典name
        Community  community = super.getById(id);
        //根据字典id获取name
        String areaName = this.dictDao.getNameById(community.getAreaId());
        community.setAreaName(areaName);
        String plateName = this.dictDao.getNameById(community.getPlateId());
        community.setPlateName(plateName);

        return community;
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }
}
