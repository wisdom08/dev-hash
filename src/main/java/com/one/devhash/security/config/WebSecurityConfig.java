package com.one.devhash.security.config;

import com.one.devhash.security.jwt.JwtAccessDeniedHandler;
import com.one.devhash.security.jwt.JwtAuthenticationEntryPoint;
import com.one.devhash.security.jwt.JwtTokenFilterConfigurer;
import com.one.devhash.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                .antMatchers("/api/users/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/api/products/**").access("isAuthenticated()")
                .antMatchers(HttpMethod.PUT, "/api/products/**").access("isAuthenticated()")
                .antMatchers(HttpMethod.DELETE, "/api/products/**").access("isAuthenticated()")
                .antMatchers(HttpMethod.POST, "/api/chats/**").access("isAuthenticated()")
                .antMatchers(HttpMethod.PUT,"/api/chats/**").access("isAuthenticated()")
                .antMatchers(HttpMethod.DELETE,"/api/chats/**").access("isAuthenticated()")
                .anyRequest().authenticated()

                .and()
                .apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
