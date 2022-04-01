package com.laowengs.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 该配置类，主要处理用户名和密码的校验等事宜
 */
@Configuration
//@EnableJpaRepositories
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {


//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }

    /**
     * 密码编码对象
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)// 设置自定义的userDetailsService
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 处理用户名和密码验证事宜
     * 1）客户端传递username和password参数到认证服务器
     * 2）一般来说，username和password会存储在数据库中的用户表中
     * 3）根据用户表中数据，验证当前传递过来的用户信息的合法性
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//            auth
//                    .inMemoryAuthentication()
//                    .withUser("admin") // 添加用户admin
//                    .password("{noop}admin")  // 不设置密码加密
//                    .roles("ADMIN", "USER")// 添加角色为admin，user
//                    .and()
//                    .withUser("user") // 添加用户user
//                    .password("{noop}user")
//                    .roles("USER")
//                    .and()
//                    .withUser("tmp") // 添加用户tmp
//                    .password("{noop}tmp")
//                    .roles(); // 没有角色
//
//    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/product/**").hasRole("USER") //添加/product/** 下的所有请求只能由user角色才能访问
                .antMatchers("/product/**").hasRole("ADMIN") //添加/product/** 下的所有请求只能由admin角色也能访问
                .antMatchers("/admin/**").hasRole("ADMIN") //添加/admin/** 下的所有请求只能由admin角色才能访问
                .anyRequest().authenticated() // 没有定义的请求，所有的角色都可以访问（tmp也可以）。
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

}
