package com.Ecomerce.bee.Handler;

import com.Ecomerce.bee.users.entity.User;
import com.Ecomerce.bee.users.service.UserService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException, java.io.IOException {
        String email = request.getParameter("username");
        User user = userService.findUserByEmail(email);
        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < 2) {
                    userService.increaseFailedAttempts(user);
                } else {
                    userService.lockUser(user);
                    exception = new LockedException("Account is locked since you have exceeded the number of trials.");
                    request.getSession().setAttribute("lockedExceptionMessage", exception.getMessage());
                }
            } else if (!user.isAccountNonLocked()) {
                boolean unlocked = userService.unlockUser(user);
                if (unlocked) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                    request.getSession().setAttribute("lockedExceptionMessage", exception.getMessage());
                }
            }
        }

        response.sendRedirect("/authentication-login?error"); // Redirect to the desired URL after authentication failure
    }
}