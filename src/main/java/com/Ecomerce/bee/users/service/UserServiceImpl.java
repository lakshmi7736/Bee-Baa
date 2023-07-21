package com.Ecomerce.bee.users.service;


import com.Ecomerce.bee.users.dao.RoleDao;
import com.Ecomerce.bee.users.dao.UserDao;
import com.Ecomerce.bee.users.dto.UserDto;
import com.Ecomerce.bee.users.entity.Role;
import com.Ecomerce.bee.users.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final int MAX_FAILED_ATTEMPTS = 3;  //limit attempt

    public static final long LOCK_TIME_DURATION = 300000;  //5 min

    private final UserDao userDao;


    private final RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        Role role = roleDao.findById("ROLE_USER").get();

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .enabled(true)
                .accountNonLocked(true)
                .failedAttempt(0)
                .lockTime(null)
                .roles(List.of(role)).build();
        userDao.save(user);
    }

    @Override
    public void saveAdmin(UserDto userDto) {
        Role role = roleDao.findById("ROLE_ADMIN").get();

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .enabled(true)
                .accountNonLocked(true)
                .failedAttempt(0)
                .lockTime(null)
                .roles(List.of(role)).build();
        userDao.save(user);

    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }


    @Override
    public void increaseFailedAttempts(User user) {
        int failedAttempts = user.getFailedAttempt() + 1;
        userDao.updateFailedAttempt(failedAttempts, user.getEmail());
    }

    @Override
    public void lockUser(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(LocalDateTime.now());
        userDao.save(user);
    }

    @Override
    public boolean unlockUser(User user) {
        LocalDateTime lockTime = user.getLockTime();
        if (lockTime != null) {
            long lockTimeInMills = lockTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            long currentTimeMillis = LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
            long unlockTimeMillis = lockTimeInMills + LOCK_TIME_DURATION;

            System.out.println(currentTimeMillis + " > " + unlockTimeMillis);
            if (currentTimeMillis > unlockTimeMillis) {
                user.setAccountNonLocked(true);
                user.setLockTime(null);
                user.setFailedAttempt(0);
                userDao.save(user);
                return true;
            }
        }
        return false;
    }
    @Override
    public void resetFailedAttempts(String email) {
        System.out.println(email);
        userDao.updateFailedAttempt(0, email);
    }

    @Override
    public List<User> getExpiredLockedUsers() {
        LocalDateTime currentTime = LocalDateTime.now();
        return userDao.findExpiredLockedUsers(currentTime);
    }


}