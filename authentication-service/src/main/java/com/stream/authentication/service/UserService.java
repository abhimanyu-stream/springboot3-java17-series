package com.stream.authentication.service;


import com.stream.authentication.dto.JwtResponse;
import com.stream.authentication.dto.LoginRequest;
import com.stream.authentication.dto.MessageResponse;
import com.stream.authentication.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;



public interface UserService {
	
	ResponseEntity<MessageResponse> signUp(SignUpRequest signUpDto);
	ResponseEntity<JwtResponse>  login(LoginRequest loginRequest);
	String generateTokenOnRefreshToken(String username);

}
