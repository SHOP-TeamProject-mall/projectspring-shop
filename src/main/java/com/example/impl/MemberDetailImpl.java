package com.example.impl;

import java.util.Collection;

import com.example.entity.Member;
import com.example.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailImpl implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(id).orElse(null);
        if (member != null) {
            String[] role = { member.getMemberrole() };
            Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(role);
            User user = new User(member.getMemberid(), member.getMemberpw(), roles);
            return user;
        }
        return null;
    }

}
