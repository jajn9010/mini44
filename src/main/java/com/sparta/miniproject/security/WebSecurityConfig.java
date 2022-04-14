package com.sparta.miniproject.security;

import com.sparta.miniproject.security.auth.FormLoginFilter;
import com.sparta.miniproject.security.auth.FormLoginSuccessHandler;
import com.sparta.miniproject.security.auth.JWTAuthProvider;
import com.sparta.miniproject.security.auth.JwtAuthFilter;
import com.sparta.miniproject.security.jwt.HeaderTokenExtractor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //private final UserDetailsServiceImpl userDetailsService;
    private final JWTAuthProvider jwtAuthProvider;
    private final HeaderTokenExtractor headerTokenExtractor;

    public WebSecurityConfig(
            JWTAuthProvider jwtAuthProvider,
            HeaderTokenExtractor headerTokenExtractor
    ) {
        this.jwtAuthProvider = jwtAuthProvider;
        this.headerTokenExtractor = headerTokenExtractor;
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean   // 비밀번호 암호화
    public PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(formLoginAuthProvider())
                .authenticationProvider(jwtAuthProvider);
    }

//    @Bean
//    public FormLoginAuthProvider formLoginAuthProvider() {
//        return new FormLoginAuthProvider((BCryptPasswordEncoder) encodePassword());
//    }

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
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);


//        http.addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers("/api/signup", "/api/login", "/user/loginCheck", "/login", "/user/loginCheck").permitAll()
                // 어떤 요청이든 '인증'
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                .antMatchers("/**", "/**/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .formLogin()
////                .loginPage("/login")
//                .loginProcessingUrl("/api/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .successHandler(restAuthenticationSuccessHandler)
//                .failureHandler(restAuthenticationFailureHandler)
//                .permitAll()
//                .and()
                .logout()
                .logoutUrl("/api/logout")
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
//    protected RestUsernamePasswordAuthenticationFilter getAuthenticationFilter(){
//        RestUsernamePasswordAuthenticationFilter authFilter = new RestUsernamePasswordAuthenticationFilter();
//        try{
//            authFilter.setFilterProcessesUrl("/api/login"); // 로그인에 대한 POST 요청을 받을 url을 정의합니다. 해당 코드가 없으면 정상적으로 작동하지 않습니다.
//            authFilter.setUsernameParameter("username");
//            authFilter.setPasswordParameter("password");
//            authFilter.setAuthenticationManager(this.authenticationManagerBean());
//            authFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
//            authFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return authFilter;
//
//    }
    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager());
        formLoginFilter.setFilterProcessesUrl("/api/login");
        formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler());
        formLoginFilter.afterPropertiesSet();
        return formLoginFilter;
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler() {
        return new FormLoginSuccessHandler();
    }

    @Bean
    public FormLoginAuthProvider formLoginAuthProvider() {

        return new FormLoginAuthProvider((BCryptPasswordEncoder) encodePassword());
    }

    private JwtAuthFilter jwtFilter() throws Exception {
        List<String> skipPathList = new ArrayList<>();

        // Static 정보 접근 허용
        skipPathList.add("GET,/images/**");
        skipPathList.add("GET,/css/**");

        // h2-console 허용
        skipPathList.add("GET,/h2-console/**");
        skipPathList.add("POST,/h2-console/**");

        // 회원 관리 API 허용
        skipPathList.add("GET,/api/**");
        skipPathList.add("POST,/api/signup");

        //로그인관련 API 허용
        skipPathList.add("GET,/user/loginCheck");
        skipPathList.add("POST,/api/login");

        skipPathList.add("GET,/");
        skipPathList.add("GET,/basic.js");

        skipPathList.add("GET,/articles");
        skipPathList.add("GET,/detail/{articleId}");

        skipPathList.add("GET,/favicon.ico");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPathList,
                "/**"
        );

        JwtAuthFilter filter = new JwtAuthFilter(
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}