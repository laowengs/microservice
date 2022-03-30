package com.laowengs.oauth2;

import com.laowengs.oauth2.entity.User;
import com.laowengs.oauth2.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class DefaultUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultUserDetailsService.class);

    private final UserRepository userRepository;

    @Autowired
    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查询用户
        User user = userRepository.findOneByUsername(username);
        if (user == null) {
            //这里找不到必须抛异常
            logger.warn("User: {} not found", user);
            throw new UsernameNotFoundException("User " + username + " was not found in db");
        }

        // 2. 设置角色
        String role = "ROLE_ANONYMOUS";
        Integer userRole = user.getUserRole();
        if(userRole != null){
            switch (userRole){
                case 1:
                    role = "ROLE_ADMIN";
                    break;
                default:
                    break;
            }
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        grantedAuthorities.add(grantedAuthority);

        return new org.springframework.security.core.userdetails.User(username,
                user.getPassword(), grantedAuthorities);
    }
}
