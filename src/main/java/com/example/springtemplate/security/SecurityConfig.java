package com.example.springtemplate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    /**
     * We can't store raw password to the database.
     * This method will hash the password and will store
     * @return hashed password
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates Role Based user
     * Here I've Created just two users
     * @return InMemoryUserDetailsManager
     */
    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     *  Filters requests according to their role
     * @param httpSecurity .
     * @return .
     * @throws Exception .
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()

                .requestMatchers("/home/admin/**").hasRole("ADMIN")//will allow all paths that begin with /home/admin
                .requestMatchers("/home/user/**").hasRole("USER")//will allow all paths that begin with /home/user
                .requestMatchers("/home/public").permitAll()//anyone can enter without authentication

                .anyRequest().authenticated()
                .and()
                .formLogin();
        return httpSecurity.build();
    }
}