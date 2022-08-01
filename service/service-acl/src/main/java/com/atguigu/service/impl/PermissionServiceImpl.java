package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseDao;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.dao.PermissionDao;
import com.atguigu.dao.RolePermissionDao;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nicc
 * @version 1.0
 * @className PermissionServiceImpl
 * @description TODO
 * @date 2022-07-29 19:40
 */

@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    //角色与权限的中间映射关系对应的dao
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        //查询所有权限
        List<Permission> permissionList = this.permissionDao.findAll();

        //查询当前角色的权限列表（ids）
        List<Long> permissionIdList = this.rolePermissionDao.findPermissionIdsByRoleId(roleId);

        //根据以上获取到的内容构建zTree
        //{ id:2, pId:0, name:"随意勾选 2", checked:true, open:true}
        List<Map<String, Object>> zNodes = new ArrayList();

        for (Permission permission : permissionList){
            Map<String, Object> zNode = new HashMap();
            zNode.put("id", permission.getId());
            zNode.put("pId", permission.getParentId());
            zNode.put("name", permission.getName());
            zNode.put("checked", permissionIdList.contains(permission.getId()));

            //设置所有目录展开，默认为fasle
            zNode.put("open","true");
            zNodes.add(zNode);
        }

        return zNodes;
    }

    @Override
    public void saveRolePermissionRelationship(Long roleId, Long[] permissionIds) {
        //删除该角色之前的权限
        this.rolePermissionDao.deletePermissionsByRoleId(roleId);


        //添加该角色现在的权限
        for(Long permissionId:permissionIds){
            if(permissionId==null) continue;
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            this.rolePermissionDao.insert(rolePermission);
        }

        /*for(Long permissionId:permissionIds){
            if(StringUtils.isEmpty(permissionId)) continue;
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);  //角色id
            rolePermission.setPermissionId(permissionId);   //权限id
            this.rolePermissionDao.insert(rolePermission);
        }*/
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        //根据adminId查询该用户的权限列表（List）
        List<Permission> permissionList = null;
        if(adminId.longValue() == 1l){
            permissionList = this.permissionDao.findAll();
        } else {
            permissionList = this.permissionDao.findMenuPermissionByAdminId(adminId);
        }

        //将List的权限列表转换为树状结构（Tree）
        List<Permission> result = PermissionHelper.bulid(permissionList);

        return result;
    }

    @Override
    public List<Permission> findAllMenu() {
        //查询全部列表
        List<Permission> permissionList = this.permissionDao.findAll();
        //若结果为空返回null
        if(CollectionUtils.isEmpty(permissionList)) return null;


        //构建树形数据,总共三级
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissionList);

        return result;
    }

    @Override
    public List<String> findCodeListByAdminId(Long adminId) {
        if(adminId==1){
            return this.permissionDao.findAllCodeList();
        }
        return this.permissionDao.findCodeListByAdminId(adminId);
    }
}
