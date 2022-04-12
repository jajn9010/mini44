package com.sparta.miniproject.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean   // 비밀번호 암호화
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                .configurationSource(corsConfigurationSource());
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers("/api/signup", "/api/login", "/api/idCheck").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                // 어떤 요청이든 '인증'
                .antMatchers("/**", "/**/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/api/login")
                .usernameParameter("userId")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .failureUrl("/login")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .permitAll();

    }


    //cors
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://192.168.219.100:8080");
        configuration.addAllowedOrigin("http://192.168.219.100:3000");
        configuration.addAllowedOrigin("http://3.39.23.124:3000");
        configuration.addAllowedOrigin("http://3.39.23.124:8080");
        configuration.addAllowedOrigin("http://dogfootdogfoot.shop");
        configuration.addAllowedOrigin("http://dogfootdogfoot.shop:8080");
        configuration.addAllowedOrigin("http://dogfootdogfoot.shop:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encodePassword());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

}