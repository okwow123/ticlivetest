package com.itac.login.service;


import com.itac.login.entity.member.MemberRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class MemberRegisterService {

    private final MemberRepository memberRepository;


    public int registerMember(String username,String password)  {
        return memberRepository.registerMember(username,password);

    }

}