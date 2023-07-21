package com.Ecomerce.bee.users.dao;


import com.Ecomerce.bee.users.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
    Optional<RefreshToken> findByRefreshToken(String token);

}