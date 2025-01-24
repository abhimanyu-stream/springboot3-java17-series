package com.stream.authentication.service;

import com.stream.authentication.configuration.JwtManager;
import com.stream.authentication.dto.JwtResponse;
import com.stream.authentication.dto.LoginRequest;
import com.stream.authentication.dto.MessageResponse;
import com.stream.authentication.dto.SignUpRequest;
import com.stream.authentication.model.RefreshToken;
import com.stream.authentication.model.Role;
import com.stream.authentication.model.RoleEnum;
import com.stream.authentication.model.User;
import com.stream.authentication.repository.RoleRepository;
import com.stream.authentication.repository.TokenRepository;
import com.stream.authentication.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;



@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtManager jwtManager;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	//@Autowired
	//AuthenticationManager authenticationManager;


	
	@Override
	//signUp
	public ResponseEntity<MessageResponse> signUp(SignUpRequest signUpRequest) {
		log.info("calling signUp....");
		
		if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();// Create a bean for BCryptPasswordEncoder
		
		// Create new user's account
				User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),passwordEncoder.encode(signUpRequest.getPassword()));
				Set<String> strRoles = signUpRequest.getRoles();
				Set<Role> roles = new HashSet<>();
				
				if (strRoles == null) {
					// Retrieving ROLE_USER from DB
					Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				} else {
					strRoles.forEach(role -> {
						
						//Create a API for upgrade/downgrade Role and specification
						switch (role) {
						case "admin | ADMIN | Admin":
							Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(adminRole);
							break;
						case "mod | MODERATOR | Moderator":
							Role modRole = roleRepository.findByName(RoleEnum.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(modRole);
							break;
						default:
							Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(userRole);
						}
					});
				}
				user.setRoles(roles);// setting default role i.e. RoleEnum.ROLE_USER
				userRepository.save(user);
				return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
}




	@Override
	//login
	public ResponseEntity<JwtResponse>  login(LoginRequest loginRequest) {// LoginRequest contains username and password
		log.info("calling login....");

		CustomUserDetails customUserDetails = null;
		try {

			 customUserDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());// 1 approach // searching in UserRepository DB
			// 2nd approach customUserDetailsService.loadUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
			

		} catch (UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
		}
		// Create RefreshToken
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(customUserDetails.getId());// Note:- User is already found in DB, then we are creating jwt-token and refresh-token

		String refreshTokenString = refreshToken.getToken();

		// Create JWT token
		if (passwordEncoder.matches(loginRequest.getPassword(), customUserDetails.getPassword())) {
			log.info("password matched");

			Map<String, Object> claims = new HashMap<>();
			claims.put("username", customUserDetails.getUsername());
			
			claims.put("email", customUserDetails.getEmail());
			List<String> authorities = Arrays.asList(customUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
			claims.put("roles", authorities);
			claims.put("userId", customUserDetails.getId());
			String subject = customUserDetails.getUsername();
			String jwtTokenCreated = jwtManager.generateJwtTokenWithSubject(claims, subject);
			
		
			//JwtResponse(Long id, String token, String type, String refreshToken, String username, String email, List<String> roles)
			return ResponseEntity.ok(new JwtResponse(customUserDetails.getId(),jwtTokenCreated,"Bearer",refreshTokenString,customUserDetails.getUsername(),customUserDetails.getEmail(),authorities));
		}

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized");
	}
	
	//generateTokenOnRefreshToken
	public String  generateTokenOnRefreshToken(String username) {
		
		CustomUserDetails userDetails;
		// Double checking user  in DB
		userDetails = customUserDetailsService.loadUserByUsername(username);

		Map<String, Object> claims = new HashMap<>();
		claims.put("username", userDetails.getUsername());
		claims.put("email", userDetails.getEmail());
		List<String> authorities = Arrays.asList(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
		claims.put("roles", authorities);
		claims.put("userId", userDetails.getId());
		String subject = userDetails.getUsername();
		String jwtjwtTokenCreatedOnRefreshToken = jwtManager.generateJwtTokenWithSubject(claims, subject);
		
		
		return jwtjwtTokenCreatedOnRefreshToken;
		
	}

	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());

		if (validUserTokens.isEmpty()) return;

		validUserTokens.forEach(t -> {
			t.setExpired(true);
			t.setRevoked(true);
		});

		tokenRepository.saveAll(validUserTokens);
	}



	public  CustomUserDetails checkUserInDBByUserName(String username){
		// by email, by username
		log.info("calling login...checkUserInDBByUserName.");

		CustomUserDetails customUserDetails = null;
		try {

			customUserDetails = customUserDetailsService.loadUserByUsername(username);// 1 approach // searching in UserRepository DB


		} catch (UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
		}
		if(customUserDetails != null) {
			return customUserDetails;
		}
		return null;// optimize it return code

	}

	public boolean isTokenExpired(String token) {
		return jwtManager.isTokenExpired(token);
	}

	public String getUsernameFromToken(String token) {
		return jwtManager.getUsernameFromToken(token);
	}

	public boolean validateTokenExternalCallFromCloudApiGateway(String token, CustomUserDetails customUserDetails) {

		return jwtManager.validateToken(token, customUserDetails);
	}
}
