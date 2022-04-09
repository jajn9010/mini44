package com.sparta.miniproject.security.auth;

import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//시큐리티 설정에서 loginProcessingUrl 해놨고, 요청이 오면 자동으로 UserDetailsService타입으로 IoC되어있는 loadUserByUsername함수가 실행됨
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //시큐리티 세션 = (Authentication(내부UserDetails))
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //
        Optional<User> userEntity = userRepository.findByUserId(userId);
        if (userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
