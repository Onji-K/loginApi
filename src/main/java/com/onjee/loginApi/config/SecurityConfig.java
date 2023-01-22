package com.onjee.loginApi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic().disable()
                .csrf().disable() //rest api 이므로 csrf 보안이 필요 없음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt 사용할 예정
                .and()
                .authorizeHttpRequests(
                        request ->
                                request.anyRequest().permitAll()
                )
                .headers().frameOptions().disable(); //추후 h2 미사용시 제거
        return http.build();
    }

}
