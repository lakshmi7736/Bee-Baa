package com.Ecomerce.bee.Handler;

import com.Ecomerce.bee.Security.JwtHelper;
import com.Ecomerce.bee.users.entity.CustomUserDetails;
import com.Ecomerce.bee.users.entity.JwtResponse;
import com.Ecomerce.bee.users.entity.RefreshToken;
import com.Ecomerce.bee.users.entity.User;
import com.Ecomerce.bee.users.service.RefreshTokenService;
import com.Ecomerce.bee.users.service.UserService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtHelper helper;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException, java.io.IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        String email= userDetails.getUsername();
        String password= userDetails.getPassword();
        if (user.getFailedAttempt() > 0) {
            userService.resetFailedAttempts(user.getEmail());
        }

        // Perform any additional tasks upon successful login


        UserDetails userDetails1= userDetailsService.loadUserByUsername(email);
        String token=helper.generateToken(userDetails1);
        System.out.println(token);
        RefreshToken refreshToken=refreshTokenService.createRefreshToken(userDetails.getUsername());
        JwtResponse response1 = JwtResponse.builder()
                        .jwtToken(token)
                                .username(userDetails1.getUsername())
                                        .refreshToken(refreshToken.getRefreshToken())
                                                .build();

// Inside the onAuthenticationSuccess method
        HttpSession session = request.getSession();
        session.setAttribute("jwtResponse", response1);

//        // Inside the onAuthenticationSuccess method
//        Cookie jwtTokenCookie = new Cookie("jwtToken", response1.getJwtToken());
//        jwtTokenCookie.setMaxAge(3600); // Set the cookie expiration time in seconds (e.g., 1 hour)
//        jwtTokenCookie.setPath("/"); // Set the cookie path to '/' to make it accessible across the entire application
//        response.addCookie(jwtTokenCookie);

        response.sendRedirect("/user/"); // Redirect to the desired URL after successful login
    }


}