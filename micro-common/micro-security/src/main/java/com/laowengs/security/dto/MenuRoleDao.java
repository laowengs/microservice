package com.laowengs.security.dto;

import com.laowengs.security.po.MenuRole;

public interface MenuRoleDao {
    int deleteByPrimaryKey(Long menuRoleId);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    MenuRole selectByPrimaryKey(Long menuRoleId);

    int updateByPrimaryKeySelective(MenuRole record);

    int updateByPrimaryKey(MenuRole record);
}