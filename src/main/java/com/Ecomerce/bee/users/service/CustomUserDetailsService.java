package com.Ecomerce.bee.users.service;


import com.Ecomerce.bee.users.dao.UserDao;
import com.Ecomerce.bee.users.entity.CustomUserDetails;
import com.Ecomerce.bee.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDao.findUserByEmail(email);
        if (user != null) {
            return new CustomUserDetails(user);
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
}