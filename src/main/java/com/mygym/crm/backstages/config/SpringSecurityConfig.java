package com.mygym.crm.backstages.config;

import com.mygym.crm.backstages.core.services.security.filter.BruteForceProtectionFilter;
import com.mygym.crm.backstages.core.services.security.CustomAuthenticationFailureHandler;
import com.mygym.crm.backstages.core.services.security.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private BruteForceProtectionFilter bruteForceProtectionFilter;
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public void setBruteForceProtectionFilter(BruteForceProtectionFilter bruteForceProtectionFilter) {
        this.bruteForceProtectionFilter = bruteForceProtectionFilter;
    public void setCustomAuthenticationFailureHandler(CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    @Autowired
    public void setCustomAuthenticationSuccessHandler(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, "/users/trainees", "/users/trainers").permitAll()
                        // Permit unauthenticated access to homepage and public pages
                        .requestMatchers("/", "/home", "/login").permitAll()
                        // For other methods on /users/trainees and /users/trainers, authentication is required
                        .requestMatchers(HttpMethod.GET, "/users/trainees", "/users/trainers").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/users/trainees", "/users/trainers").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/trainees", "/users/trainers").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/users/trainees", "/users/trainers").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureHandler(customAuthenticationFailureHandler)
                        .successHandler(customAuthenticationSuccessHandler)
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcManager = new JdbcUserDetailsManager(dataSource);

        // Custom query for User authentication (username, password, enabled)
        jdbcManager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM user_table WHERE username = ?"
        );

        // Custom query for Role/Authority retrieval for authenticated users
        jdbcManager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, a.authority FROM user_table u JOIN authorities_table a ON u.user_id = a.user_id WHERE u.username = ?"
        );


        return jdbcManager;
    }

}
