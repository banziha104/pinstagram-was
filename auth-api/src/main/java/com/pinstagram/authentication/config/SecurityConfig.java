package com.pinstagram.authentication.config;

import com.pinstagram.common.jwt.AuthManager;
import com.pinstagram.common.jwt.JwtManager;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtManager jwtManager(){
        return new JwtManager();
    }

    @Bean
    public AuthManager authManager(){
        return new AuthManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter filter =
                new JwtAuthenticationFilter(authenticationManager(), jwtManager());
        http.cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

        private JwtManager jwtUtil;

        public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtManager jwtUtil) {
            super(authenticationManager);
            this.jwtUtil = jwtUtil;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain) throws IOException, ServletException {
            Authentication authentication = getAuthentication(request);
            if (authentication != null) {
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        }

        private Authentication getAuthentication(HttpServletRequest request) {
            String token = request.getHeader("Authorization");
            if (token == null) {
                return null;
            }
            Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));
            return new UsernamePasswordAuthenticationToken(claims, null);
        }
    }
}