package com.backend.spring.user.security;

import com.backend.spring.user.security.filters.SimpleAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final AppUserAuthenticationProvider authenticationProvider;

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SimpleAuthenticationFilter authenticationFilter = new SimpleAuthenticationFilter(authenticationConfiguration.getAuthenticationManager());
        authenticationFilter.setFilterProcessesUrl("/api/login");

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
        http.httpBasic(withDefaults());

        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.addFilter(authenticationFilter);

        // TODO: Allow specific route access based on role later on
//        http.authorizeRequests().antMatchers(GET, "/api/**").permitAll();
//        http.authorizeRequests()
//                .anyRequest()
//                .hasAnyRole(SHOP_OWNER.getValue(), VEHICLE_OWNER.getValue())
//                .and()
//                .httpBasic(withDefaults())
//                .sessionManagement()
//                .sessionCreationPolicy(STATELESS);

        return http.build();
    }
}
