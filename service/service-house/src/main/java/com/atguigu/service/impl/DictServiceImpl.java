package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.DictDao;
import com.atguigu.entity.Dict;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @className DictServiceImpl
* @description TODO
* @author nicc
* @date 2022-07-23 09:41
* @version 1.0
*/
@Service(interfaceClass = DictService.class)
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {

    @Autowired
    private DictDao dictDao;
    @Override
    protected BaseDao<Dict> getEntityDao() {
        return this.dictDao;
    }

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        //调用Dao查询指定id的子元素列表
        List<Dict> dictList = dictDao.findListByParentId(id);

        //将List<Dict>转换为List<Map>,（前台zTree需要的json格式）
        List<Map<String,Object>> zNodes = new ArrayList<>();
        /*
        [{
            id: '031',
            name: 'n3.n1',
            isParent: true
        }, {
            id: '032',
            name: 'n3.n2',
            isParent: false
        }]
         */
        for(Dict dict:dictList){
            Map<String,Object> zNode = new HashMap<>();
            zNode.put("id",dict.getId());
            zNode.put("name",dict.getName());
            int count = this.dictDao.countIsParent(dict.getId());
            zNode.put("isParent",count>0?true:false);

            zNodes.add(zNode);
        }
        return zNodes;
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        //根据dictCode找到Dict
        Dict dict = this.dictDao.getByDictCode(dictCode);
        if(null == dict) return null;
        //从Dict对象中获取Id
        Long id = dict.getId();
        //将id作为parentId查询所有下级元素并返回
        List<Dict> dictList = this.dictDao.findListByParentId(id);
        return dictList;
    }

    @Override
    public List<Dict> findListByParentId(Long parentId) {
        List<Dict> dictList = dictDao.findListByParentId(parentId);
        return dictList;
    }

    @Override
    public String getNameById(Long dictId) {
        return dictDao.getNameById(dictId);
    }
}
