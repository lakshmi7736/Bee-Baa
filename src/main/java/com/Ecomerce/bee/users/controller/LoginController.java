package com.Ecomerce.bee.users.controller;


import com.Ecomerce.bee.Security.JwtHelper;
import com.Ecomerce.bee.users.dto.UserDto;
import com.Ecomerce.bee.users.entity.*;
import com.Ecomerce.bee.users.service.RefreshTokenService;
import com.Ecomerce.bee.users.service.RoleServices;
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
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleServices roleService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final AuthenticationManager manager;

    private final UserDetailsService userDetailsService;

    private final JwtHelper helper;



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

        userService.saveAdmin(userDto);
        return "redirect:/authentication-register?success";
    }


    @PostMapping("/role")
    public Role createRole(@RequestBody Role role){
        return  roleService.createNewRole(role);

    }
}