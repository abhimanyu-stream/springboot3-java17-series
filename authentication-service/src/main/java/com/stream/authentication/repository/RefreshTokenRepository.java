package com.stream.authentication.repository;


import com.stream.authentication.model.RefreshToken;
import com.stream.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;



@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	RefreshToken findByToken(String token);

	//RefreshToken findByToken(String token);

	@Modifying
	Long deleteByUser(User user);

	// The method map(refreshTokenService::verifyExpiration) is undefined for the
	// type Optional<RefreshToken>

}
