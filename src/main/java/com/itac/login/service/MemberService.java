package com.itac.login.service;

import com.itac.login.entity.user.Users;
import com.itac.login.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

	private final UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


		Users member = userRepository.findByUserEmail(email);
		
		if (member == null) throw new UsernameNotFoundException("Not Found account."); 
		
		return member;
	}


	public int registerMember(String username,String password,String auth)  {
		return userRepository.registerUser(username,password,auth);
	}

}