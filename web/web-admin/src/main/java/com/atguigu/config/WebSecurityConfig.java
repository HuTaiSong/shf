package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author nicc
 * @version 1.0
 * @className WebSecurityConfig
 * @description TODO
 * @date 2022-07-30 00:32
 */

@Configuration //这是一个配置类
@EnableWebSecurity //启用Spring Security
@EnableGlobalMethodSecurity(prePostEnabled = true)  //启用在方法上进行授权管理
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
//        auth.inMemoryAuthentication().withUser("lucky")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("admin","manager");
//
//        auth.inMemoryAuthentication().withUser("happy")
//                .password(new BCryptPasswordEncoder().encode("654321"))
//                .roles("");
//    }


    @Bean //将该方法创建的对象加入到IoC容器
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //这里还不要注释
        //super.configure(http);

        //让iframe也可以正常访问
        http.headers().frameOptions().sameOrigin();

        //允许静态资源不经过认证就可以访问
        http.authorizeRequests()
                .antMatchers("/static/**", "/loginPage").permitAll() // /static/** 可以匿名访问, 登陆页面也要放行
                .anyRequest().authenticated();  //其他页面全部需要验证

        http.formLogin()
                .loginPage("/loginPage")  //表单页面地址
                //.usernameParameter("username") // 如果省略，必须是username
                //.passwordParameter("password") //如果省略，必须是password
                .loginProcessingUrl("/login") //提交表单的处理地址
                //.failureForwardUrl("/loginPage") //登录失败后哪里 若省略默认为:loginPage?error
                .defaultSuccessUrl("/"); //登录成功后哪里

        //配置注销
        http.logout()
                .logoutUrl("/logout") //退出登陆的路径，指定spring security拦截的注销url,退出功能是security提供的
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/loginPage"); //用户退出后要被重定向的url


        http.csrf().disable(); //关闭跨域请求伪造功能

        //添加自定义无权限处理器
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
