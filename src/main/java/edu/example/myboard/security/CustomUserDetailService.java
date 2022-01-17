package edu.example.myboard.security;

import edu.example.myboard.dto.Member;
import edu.example.myboard.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@ComponentScan
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member userInfo = memberMapper.findByUsername(username);

        if(userInfo == null){
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 업습니다: "+username);
        }

        return new CustomUserDetails(userInfo);
    }
}
