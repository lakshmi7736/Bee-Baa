package com.Ecomerce.bee.users.service;


import com.Ecomerce.bee.users.dto.UserDto;
import com.Ecomerce.bee.users.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    void saveAdmin(UserDto userDto);
    User findUserByEmail(String email);
    void increaseFailedAttempts(User user);
    void lockUser(User user);

    boolean unlockUser(User user);

    void resetFailedAttempts(String email);

    List<User> getExpiredLockedUsers();

}