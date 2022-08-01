package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nicc
 * @version 1.0
 * @className MyUserDetailService
 * @description TODO
 * @date 2022-07-30 11:45
 */

@Component
public class MyUserDetailService implements UserDetailsService {

    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据username获取Admin对象
        Admin admin = adminService.getByUsername(username);

        if(admin ==null ){ //用户名错误，登录肯定失败
            throw new UsernameNotFoundException("用户名错误");
        }else{ //用户名正确，登录不一定成功，因为还有密码呢？不需要开发者进行密码的比较
//            List <GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority("admin_add"));
//            authorities.add(new SimpleGrantedAuthority("role_update"));
            List<String> codeList = this.permissionService.findCodeListByAdminId(admin.getId());
            List <GrantedAuthority> authorities = new ArrayList<>();
            for(String code :codeList){
                GrantedAuthority authority = new SimpleGrantedAuthority(code);
                authorities.add(authority);
            }
            /**
             * username:用户名
             * password：数据库中正确的密码
             * authorities 用户登录成功后要赋予的权限
             * 后面会由SpringSecurity来进行密码的比较，如果正确，授予指定权限authorities
             */
            return new User(username,admin.getPassword(),authorities);
        }

    }
}
