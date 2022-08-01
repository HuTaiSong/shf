package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.HouseImageDao;
import com.atguigu.entity.HouseImage;
import com.atguigu.service.HouseImageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className HouseImageServiceImpl
 * @description TODO
 * @date 2022-07-25 20:27
 */
@Service(interfaceClass = HouseImageService.class)
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {

    @Autowired
    private HouseImageDao houseImageDao;

    @Override
    protected BaseDao<HouseImage> getEntityDao() {
        return houseImageDao;
    }

    @Override
    public List<HouseImage> findList(Long houseId, int i) {
        return houseImageDao.findList(houseId, i);
    }
}
