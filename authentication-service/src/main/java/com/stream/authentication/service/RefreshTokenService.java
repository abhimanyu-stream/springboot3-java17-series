package com.stream.authentication.service;



import com.stream.authentication.exception.TokenRefreshException;
import com.stream.authentication.model.RefreshToken;
import com.stream.authentication.repository.RefreshTokenRepository;
import com.stream.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

	@Value("${app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;
	private RefreshTokenRepository refreshTokenRepository;
	private UserRepository userRepository;
	@Autowired
	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository){
		this.refreshTokenRepository =refreshTokenRepository;
		this.userRepository=userRepository;
	}

	public RefreshToken findByRefreshToken(String token) {
		// String token is of type RefreshToken
		
		
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(Long userId) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepository.findById(userId).get());// Find User in DB
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		//120,000 milliseconds is equal to 2 minutes (or 2 minutes and 0 seconds).
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.saveAndFlush(refreshToken);// Delete previous toke from DB
		return refreshToken;
	}

	@Transactional
	public Long deleteByUserId(Long userId) {
		return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	}

	
	public RefreshToken verifyRefreshTokenExpiration(RefreshToken refreshToken) throws TokenRefreshException {

		if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
			//delete invalid refreshToken from db
			refreshTokenRepository.delete(refreshToken);
			System.out.println(refreshToken.getToken() + "   Refresh token was expired. Please make a new signin request");
			throw  new TokenRefreshException(refreshToken.getToken(), "Refresh token was expired. Please make a new signin request");
		}

		return refreshToken;// now still RefreshToken is valid
	}
}