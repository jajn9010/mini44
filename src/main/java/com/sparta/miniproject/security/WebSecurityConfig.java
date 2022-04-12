package com.sparta.miniproject.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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
    @Override public void configure(WebSecurity web) {
       web .ignoring() .antMatchers("/h2-console/**"); }

        @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()   //csrf 비활성화하고자 하는 경우
//                .csrf()
//                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                    .and()
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/signup").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/h2-console/**/**").permitAll()
//                .antMatchers("/", "home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/index.html")
                .loginProcessingUrl("/api/login")
                .usernameParameter("userId")
                .passwordParameter("password")
                .defaultSuccessUrl("/api/login")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .permitAll();

    }

//    private Filter usernamePasswordAuthenticationFilter() {
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Bean
//    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
//        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
//        filter.setFilterProcessesUrl("/api/login");
//        filter.setAuthenticationManager(authenticationManagerBean());
//        filter.setAuthenticationSuccessHandler();
//        filter.setAuthenticationFailureHandler();
//        filter.setUsernameParameter("userId");
//        filter.setPasswordParameter("password");
//        return filter;
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        return new CustomAuthenticationProvider(userDetailsService, encodePassword);
//    }

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