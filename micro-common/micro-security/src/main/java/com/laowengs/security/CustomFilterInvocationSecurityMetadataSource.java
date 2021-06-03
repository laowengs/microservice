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
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String,String> pathRoleMap = new HashMap<>();
        for (Menu menu : allMenus) {
            if (antPathMatcher.match(menu.getPath(), requestUrl)) {
                Long menuId = menu.getMenuId();
                List<MenuRole> menuRoles = menuRoleDao.selectByMenuId(menuId);
                for (MenuRole menuRole : menuRoles) {
                    Long roleId = menuRole.getRoleId();
                    Role role = roleDao.selectByPrimaryKey(roleId);
                    pathRoleMap.put(menu.getPath(),role.getRoleName());
                }
            }
        }
        RoleEnum roleEnum = RoleEnum.ROLE_LOGIN;
        for (Map.Entry<String, String> pathEntry : pathRoleMap.entrySet()) {
            //一个路径匹配到多个权限配置，取权限最大的
            RoleEnum roleEnum1 = RoleEnum.valueOf(pathEntry.getValue());
            if(roleEnum1.getPriority() > roleEnum.getPriority()){
                roleEnum = roleEnum1;
            }
        }
        return SecurityConfig.createList(roleEnum.name());
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
