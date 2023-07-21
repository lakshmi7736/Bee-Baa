package com.Ecomerce.bee.users.service;

import com.Ecomerce.bee.users.dao.RoleDao;
import com.Ecomerce.bee.users.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServices{
    @Autowired
    private RoleDao roleRepository;
    public Role createNewRole(Role role){
        return roleRepository.save(role);
    }
}
