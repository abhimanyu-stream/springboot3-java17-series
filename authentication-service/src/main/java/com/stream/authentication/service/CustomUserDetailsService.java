package com.stream.authentication.service;


import com.stream.authentication.model.User;
import com.stream.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	//@Autowired
	//private RoleRepository roleRepository;
	
	

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//1.
		User user = userRepository.findByUsername(username);
		
		
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(user);

		System.out.println("Inside CustomUserDetailsService " + customUserDetails.getAuthorities());
		System.out.println("Inside CustomUserDetailsService " + customUserDetails.getUsername());
		System.out.println("Inside CustomUserDetailsService " + customUserDetails.getPassword());
		System.out.println("Inside CustomUserDetailsService " + customUserDetails.getId());
		System.out.println("Inside CustomUserDetailsService " + customUserDetails.getEmail());
		return customUserDetails;
	}

	public CustomUserDetails loadUserByUsernameAndPassword(String username, String password) {
		
		User user = userRepository.findByUsernameAndPassword(username,password);
		if(user == null) {
			throw new UsernameNotFoundException("User not found with Username " + username + "  | Password " + password);
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return customUserDetails;
	}


}
