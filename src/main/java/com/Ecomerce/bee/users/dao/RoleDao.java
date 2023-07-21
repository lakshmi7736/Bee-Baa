package com.Ecomerce.bee.users.dao;


import com.Ecomerce.bee.users.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role,String> {
    Role findByName(String roleName);
}