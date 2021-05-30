package com.laowengs.security.po;

import java.io.Serializable;

/**
 * menu
 * @author 
 */
public class Menu implements Serializable {
    private Long menuId;

    private String path;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId=" + menuId +
                ", path='" + path + '\'' +
                '}';
    }
}