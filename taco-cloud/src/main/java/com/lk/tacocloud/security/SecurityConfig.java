package com.lk.tacocloud.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.lk.tacocloud.domain.User;
import com.lk.tacocloud.repository.UserRepository;

@Configuration

public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) return user;

            throw new UsernameNotFoundException("User '" + username + "' not found.");
        };
    }

    /**
     *  The following are among the many things you can configure with HttpSecurity:
         Requiring that certain security conditions be met before allowing a request to
        be served
         Configuring a custom login page
         Enabling users to log out of the application
         Configuring cross-site request forgery protection
     * @param http
     * @return
     * @throws Exception
     */
    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
         * Requests for /design and /orders should be for users with a granted authority
            of ROLE_USER. Don’t include the ROLE_ prefix on roles passed to hasRole(); it
            will be assumed by hasRole().
             All requests should be permitted to all users.
         */
        return http
            .csrf().disable() // remove disable...
            .authorizeHttpRequests()
                .requestMatchers("/design", "/orders").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/ingredients").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/ingredients/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            // .requestMatchers("/", "/**").permitAll()
            .and()
                .formLogin()
                    .loginPage("/login")
            .and()
                .logout()
                    .logoutSuccessUrl("/")
            .and()
                .headers()
                .frameOptions()
                .sameOrigin()
            .and()
            .build();
    }

    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    //     List<UserDetails> usersList = new ArrayList<>();
    //     usersList.add(new User("buzz", passwordEncoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
    //     usersList.add(new User("woody", passwordEncoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
    //     return new InMemoryUserDetailsManager(usersList);
    // }
}
