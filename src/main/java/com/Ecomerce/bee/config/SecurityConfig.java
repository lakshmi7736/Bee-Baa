package com.Ecomerce.bee.config;



import com.Ecomerce.bee.Handler.LoginFailureHandler;
import com.Ecomerce.bee.Handler.LoginSuccessHandler;
import com.Ecomerce.bee.Security.JwtAuthenticationEntryPoint;
import com.Ecomerce.bee.Security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private  LoginFailureHandler loginFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;


    @Bean
    public DaoAuthenticationProvider doDaoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/Admin","/icon-tabler","/ui-buttons","/ui-alerts","/ui-card","/ui-typography","/ui-forms","/sample-page").hasRole("ADMIN")
                        .requestMatchers("/registration/**","/registrationAdmin/**").permitAll()
                        .requestMatchers("/authentication-login/**","/authentication-register/**","/assets/**").permitAll()
                        .requestMatchers("/","/Womens","/frontEnd/**","/Mens","/shop","/product-details","/shop-cart","/checkout","/blog-details","/blog","/contact").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/authentication-login")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                        .loginProcessingUrl("/authentication-login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                ;
//                .exceptionHandling(eh->eh.accessDeniedPage("/access-denied"));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



}