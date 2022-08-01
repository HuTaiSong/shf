package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.AdminRoleDao;
import com.atguigu.dao.RoleDao;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className RoleServiceImpl
 * @description TODO
 * @date 2022-07-19 14:15
 */
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;
    //引入AdminRoleDao
    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }

    @Override
    public List<Role> findAll() {
        return this.roleDao.findAll();
    }

    //获取用户角色分配信息
    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        //查询所有角色
        List<Role> allRolesList = roleDao.findAll();

        //获取拥有的角色id列表
        List<Long> existRoleIdList = adminRoleDao.findRoleIdsByAdminId(adminId);

        //对角色进行分类
        List<Role> noAssignRoleList = new ArrayList<>();//可添加角色列表
        List<Role> assignRoleList = new ArrayList<>();//已拥有角色列表

        for(Role role : allRolesList){
            //已分配
            if(existRoleIdList.contains(role.getId())){
                assignRoleList.add(role);
            } else {
                noAssignRoleList.add(role);
            }
        }

        //将已拥有角色列表和可选角色列表封装进map
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("noAssignRoleList", noAssignRoleList);
        roleMap.put("assignRoleList", assignRoleList);

        return roleMap;
    }

    //分配角色
    @Override
    public void saveUserRelationShip(Long adminId, Long[] roleIds) {
        //删除之前的角色
        this.adminRoleDao.deleteByAdminId(adminId);

        //添加现在分配的角色
        for(Long roleId :roleIds){
            if(StringUtils.isEmpty(roleId)) continue; //有多余的逗号，产生一个null的roleId
                AdminRole adminRole = new AdminRole();
                adminRole.setAdminId(adminId);
                adminRole.setRoleId(roleId);
                this.adminRoleDao.insert(adminRole);
        }
    }



    /*public Integer insert(Role role){
        return this.roleDao.insert(role);
    }

    @Override
    public Role getById(Serializable id) {
        return roleDao.getById(id);
    }

    @Override
    public Integer update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public Integer delete(Long id) {
        return roleDao.delete(id);
    }

    @Override
    public PageInfo<Role> findByPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        //启用分页插件:
        // select count(*)....获取符号查询条件的记录总数
        //给查询条件添加limit 子句
        PageHelper.startPage(pageNum, pageSize);

        Page<Role> page = this.roleDao.findByPage(filters);
        //navigatePages:能够同时在分页栏显示的最大页码数
        return new PageInfo<>(page, 10);
    }*/
}
