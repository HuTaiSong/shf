package com.atguigu.dao;

import com.atguigu.base.BaseDao;
import com.atguigu.entity.Dict;

import java.util.List;

/**
* @className DictDao
* @description TODO
* @author nicc
* @date 2022-07-23 09:42
* @version 1.0
*/

public interface DictDao extends BaseDao<Dict> {

    List<Dict> findListByParentId(Long parentId);
    //判断一个节点的孩子的数量，从而判断该节点是否是父节点
    Integer countIsParent(Long id);

    Dict getByDictCode(String dictCode);

    String getNameById(Long dictId);
}
