package com.sparta.miniproject.security.auth;

import com.sparta.miniproject.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

//로그인 진행완료되면 시큐리티 session을 만듦. Security ContextHolder에 session저장.
//오브젝트는 정해져있고 Authentication 타입 객체만 들어갈수있음
//Authentication 안에 User 정보가 있어야 함.
//User 오브젝트 타입은 UserDetails타입 객체만 가능
//Security Session에는 Authentication 타입만 들어갈 수 있고 그안의 User객체는 UserDetails타입만 들어갈 수 있음.
public class PrincipalDetails implements UserDetails {

    private User user; //콤포지션

    public PrincipalDetails(Optional<User> userEntity) {
    }
//    public PrincipalDetails(User user){
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return null;
//            }
//        });
//        this.user = user;
//    }
//
//    //해당 유저의 권한을 리턴 함.
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //회원이 1년동안 로그인 안하면 휴면계정 화 함
        return true;
    }
}
