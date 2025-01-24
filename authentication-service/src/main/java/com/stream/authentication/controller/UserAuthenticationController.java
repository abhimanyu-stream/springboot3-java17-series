package com.stream.authentication.controller;




import com.stream.authentication.dto.*;
import com.stream.authentication.exception.TokenRefreshException;
import com.stream.authentication.model.RefreshToken;
import com.stream.authentication.model.User;
import com.stream.authentication.service.CustomUserDetails;
import com.stream.authentication.service.RefreshTokenService;
import com.stream.authentication.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@SecurityRequirement(name = "Bearer Authentication")//its a class level configuration

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserAuthenticationController {


	private UserServiceImpl userService;
	private RefreshTokenService refreshTokenService;
	@Autowired
	public UserAuthenticationController(UserServiceImpl userService, RefreshTokenService refreshTokenService) {
		this.userService = userService;
		this.refreshTokenService = refreshTokenService;
	}

	/**
	 * API to signUp
	 * 
	 * @paramThe SignUpRequest that contains email, username and password. The Role
	 *            is optional. If User do not provide Role then default Role is
	 *            assigned to user i.e. ROLE_USER. [ ROLE_MODERATOR, ROLE_ADMIN,
	 *            ROLE_USER]
	 * @return Returns successful message
	 * @see
	 */
//users/signup
	@PostMapping(path = "/signup", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MessageResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
		System.out.println("inside signUp");

		return (userService.signUp(signUpRequest));
	}

	/**
	 * API to Login
	 * 
	 * @paramThe LoginRequest that contains username and password
	 * @return Returns the JWT token
	 * @see
	 */

	@PostMapping(path = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
		System.out.println("inside login");

		return (userService.login(loginRequest));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshToken(@RequestBody @Validated TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();
		RefreshToken refreshToken = refreshTokenService.findByRefreshToken(requestRefreshToken);
		if (refreshToken == null) {
			System.out.println(requestRefreshToken
					+ "   Refresh token is not in database! Please login again by username and password!");
			throw new TokenRefreshException(requestRefreshToken,
					"Refresh token is not in database! Please login again by username and password!");
		}

		// else// RefreshToken is found in DB , now check its validity, NOTE:- here
		// return value will come only if RefreshToken is still valid , and if non-valid
		// then there only exception thrown
		RefreshToken refreshTokenCheckedAndStillValid = refreshTokenService.verifyRefreshTokenExpiration(refreshToken);
		User user = refreshTokenCheckedAndStillValid.getUser();
		Long userId = user.getId(); // find all cliams and put into token
		String token = userService.generateTokenOnRefreshToken(user.getUsername());
		return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));

	}

	// @Deprecated
	// T getById(ID id);

	@PostMapping("/logout")
	public ResponseEntity<MessageResponse> logoutUser(@RequestBody @Validated LogOutRequest logOutRequest) {

		Long userId = refreshTokenService.deleteByUserId(logOutRequest.getUserId());
		return ResponseEntity.ok(new MessageResponse("User Log out successful with PK !" + userId));
	}
	@GetMapping("/validate/token/{token}")
	public ResponseEntity<MessageResponse> validateToken(@PathVariable String token, @RequestHeader("loggedInUser") String username){

		log.info("username has requested...");
		CustomUserDetails customUserDetails;
		//String requestedUser = null;
		boolean isTokenExpired = userService.isTokenExpired(token);
		if(!isTokenExpired){
			userService.getUsernameFromToken(token);
		}
		customUserDetails = userService.checkUserInDBByUserName(username);
		boolean result = userService.validateTokenExternalCallFromCloudApiGateway(token, customUserDetails);
		MessageResponse messageResponse = null;
		if(result){
			messageResponse = new MessageResponse(username);
			return ResponseEntity.ok(messageResponse);
		}



		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("User not found"));

	}



}
