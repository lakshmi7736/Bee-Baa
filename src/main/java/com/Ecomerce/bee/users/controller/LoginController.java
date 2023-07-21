package com.Ecomerce.bee.users.controller;


import com.Ecomerce.bee.Security.JwtHelper;
import com.Ecomerce.bee.users.dto.UserDto;
import com.Ecomerce.bee.users.entity.JwtRequest;
import com.Ecomerce.bee.users.entity.JwtResponse;
import com.Ecomerce.bee.users.entity.RefreshToken;
import com.Ecomerce.bee.users.entity.User;
import com.Ecomerce.bee.users.service.RefreshTokenService;
import com.Ecomerce.bee.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final AuthenticationManager manager;

    private final UserDetailsService userDetailsService;

    private final JwtHelper helper;


    @RequestMapping("/authentication-login")
    public String loginForm() {
        return "authentication-login";
    }



    @GetMapping("/authentication-register")
    public String registrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "authentication-register";
    }

    @PostMapping("/registration")
    public String registration(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null)
            result.rejectValue("email", null,
                    "User already registered !!!");

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);

            return "/authentication-register";
        }

        userService.saveUser(userDto);
        return "redirect:/authentication-register?success";
    }

    @PostMapping("/registrationAdmin")
    public String registrationAdmin(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null)
            result.rejectValue("email", null,
                    "User already registered !!!");

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/authentication-register";
        }

        userService.saveUser(userDto);
        return "redirect:/authentication-register?success";
    }
}