package com.FitInfo.FitInfo.global.config;

import com.FitInfo.FitInfo.domain.user.UserDetailsServiceImpl;
import com.FitInfo.FitInfo.global.jwt.JwtAuthenticationFilter;
import com.FitInfo.FitInfo.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtUtil jwtUtil;

  private final UserDetailsServiceImpl userDetailsService;

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf((csrf) -> csrf.disable());

    httpSecurity.sessionManagement((sessionManagement) ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

    httpSecurity.authorizeHttpRequests((authorizeHttpRequests) ->
        authorizeHttpRequests
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers(HttpMethod.POST,("/api/user/signup")).permitAll()
            .requestMatchers(HttpMethod.GET,("/api/user/login")).permitAll()
            .requestMatchers(HttpMethod.GET,("/api/user/signup-page")).permitAll()
            .anyRequest().authenticated()
        );

    httpSecurity.addFilterBefore(jwtAuthenticationFilter(),
        UsernamePasswordAuthenticationFilter.class
    );

    return httpSecurity.build();
  }
}
