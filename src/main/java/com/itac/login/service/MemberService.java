package com.itac.login.service;

import com.itac.login.entity.member.Member;
import com.itac.login.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


		Member member = memberRepository.findByEmail(email);
		
		if (member == null) throw new UsernameNotFoundException("Not Found account."); 
		
		return member;
	}


	public int registerMember(String username,String password)  {
		return memberRepository.registerMember(username,password);
	}

	public String getAuth(String username){
		return userRepository.getAuth(username);
	}

}