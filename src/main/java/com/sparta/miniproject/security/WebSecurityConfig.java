package com.sparta.miniproject.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    @Autowired
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
    @Autowired
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
    @Autowired
    private RestLogoutSucccessHandler restLogoutSucccessHandler;

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
        http.headers().httpStrictTransportSecurity()
                .maxAgeInSeconds(0)
                .includeSubDomains(true);

        http
                .cors()
                .configurationSource(corsConfigurationSource());
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.formLogin().disable()
        .httpBasic().disable();
        http.addFilterAt(getAuthenticationFilter(), RestUsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers("/api/signup", "/api/login", "/user/loginCheck", "/login", "/user/loginCheck").permitAll()
                // 어떤 요청이든 '인증'
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                .antMatchers("/**", "/**/**").permitAll()
                .anyRequest().authenticated()

//                .loginPage("/login")
//                .loginProcessingUrl("/api/login")
//                .usernameParameter("userId")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/")
//                .failureUrl("/login")
//                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(restLogoutSucccessHandler)
                .logoutSuccessUrl("/").permitAll();

    }


    //cors
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://192.168.219.100:8080");
        configuration.addAllowedOrigin("http://192.168.219.100:3000");
        configuration.addAllowedOrigin("http://54.180.90.59:3000");
        configuration.addAllowedOrigin("http://54.180.90.59:8080");
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

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    //    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
////        auth.authenticationProvider(daoAuthenticationProvider());
////    }
//    @Bean
//    DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(encodePassword());
//        provider.setUserDetailsService(userDetailsService());
//        return provider;
//    }
protected RestUsernamePasswordAuthenticationFilter getAuthenticationFilter(){
    RestUsernamePasswordAuthenticationFilter authFilter = new RestUsernamePasswordAuthenticationFilter();
    try{
        authFilter.setFilterProcessesUrl("/api/login"); // 로그인에 대한 POST 요청을 받을 url을 정의합니다. 해당 코드가 없으면 정상적으로 작동하지 않습니다.
        authFilter.setUsernameParameter("username");
        authFilter.setPasswordParameter("password");
        authFilter.setAuthenticationManager(this.authenticationManagerBean());
        authFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
        authFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
    } catch (Exception e){
        e.printStackTrace();
    }
    return authFilter;
}
}