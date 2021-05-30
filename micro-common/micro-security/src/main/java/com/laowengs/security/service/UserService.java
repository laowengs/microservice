package com.laowengs.security.service;

import com.laowengs.security.dto.RoleDao;
import com.laowengs.security.dto.UserDao;
import com.laowengs.security.po.Role;
import com.laowengs.security.po.User;
import com.laowengs.security.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.selectByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("账户不存在!");
        }
        Role role = roleDao.selectByPrimaryKey(user.getUserId());
        return new UserVo(user, Collections.singletonList(role));
    }
}
