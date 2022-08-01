package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className DictService
 * @description TODO
 * @date 2022-07-23 09:39
 */
public interface DictService extends BaseService<Dict> {

    /**
     * 查询指定id的下级元素
     * @param id   字典项id
     * @return  不是List<Dict>,而是List<Map>，已经根据前台需要的json格式进行了转换
     */
    List<Map<String,Object>> findZnodes(Long id);

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    List<Dict> findListByDictCode(String dictCode);

    List<Dict> findListByParentId(Long parentId);

    String getNameById(Long dictId);

}
