package com.laowengs.security;

import com.laowengs.security.dto.MenuDao;
import com.laowengs.security.dto.MenuRoleDao;
import com.laowengs.security.dto.RoleDao;
import com.laowengs.security.po.Menu;
import com.laowengs.security.po.MenuRole;
import com.laowengs.security.po.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    // 创建一个AnipathMatcher，主要用来实现ant风格的URL匹配。
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private MenuRoleDao menuRoleDao;
    @Autowired
    private RoleDao roleDao;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 从参数中提取出当前请求的URL
        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        // 从数据库中获取所有的资源信息，即本案例中的menu表以及menu所对应的role
        // 在真实项目环境中，开发者可以将资源信息缓存在Redis或者其他缓存数据库中。
        List<Menu> allMenus = menuDao.getAllMenus();
        for (Menu menu : allMenus) {
            if (antPathMatcher.match(menu.getPath(), requestUrl)) {
                Long menuId = menu.getMenuId();
                List<MenuRole> menuRoles = menuRoleDao.selectByMenuId(menuId);
                String[] roleArr = new String[menuRoles.size()];
                for (int i = 0; i < roleArr.length; i++) {
                    Long roleId = menuRoles.get(i).getRoleId();
                    Role role = roleDao.selectByPrimaryKey(roleId);
                    roleArr[i] = role.getRoleName();
                }
                return SecurityConfig.createList(roleArr);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
