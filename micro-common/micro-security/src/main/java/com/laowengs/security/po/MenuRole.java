package com.laowengs.security.po;

import java.io.Serializable;

/**
 * menu_role
 * @author 
 */
public class MenuRole implements Serializable {
    private Long menuRoleId;

    private Long menuId;

    private Long roleId;

    private static final long serialVersionUID = 1L;

    public Long getMenuRoleId() {
        return menuRoleId;
    }

    public void setMenuRoleId(Long menuRoleId) {
        this.menuRoleId = menuRoleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}