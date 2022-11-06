package com.backend.spring.user.security;

import com.backend.spring.user.security.filters.SimpleAuthenticationFilter;
import com.backend.spring.user.security.filters.SimpleAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.backend.spring.user.security.SecurityConstants.LOGIN_URL;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SimpleAuthenticationFilter authenticationFilter = new SimpleAuthenticationFilter(authenticationConfiguration.getAuthenticationManager());
        authenticationFilter.setFilterProcessesUrl(LOGIN_URL);

        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
        http.httpBasic(withDefaults());
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.addFilter(authenticationFilter);
        http.addFilterBefore(new SimpleAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        // TODO: Allow specific route access based on role later on
//        http.authorizeRequests().antMatchers(GET, "/api/**").permitAll();
//        http.authorizeRequests()
//                .anyRequest()
//                .hasAnyRole(SHOP_OWNER.getValue())
//                .and()
//                .httpBasic(withDefaults())
//                .sessionManagement()
//                .sessionCreationPolicy(STATELESS);

        return http.build();
    }
}
