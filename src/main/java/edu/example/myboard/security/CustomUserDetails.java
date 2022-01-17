package edu.example.myboard.security;

import edu.example.myboard.dto.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Member member;

    public CustomUserDetails(Member member){
        this.member = member;
    }

    public void setUsername(String username) {
    }

    public void setPassword(String password) {
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    }

    @Override
    public String getUsername() {

        return member.getId();
    }

    @Override
    public String getPassword() {

        return member.getPwd();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(()->{return member.getAuthority();});
        return collection;
    }
}
