package com.stream.authentication.configuration;

import java.util.*;
import java.util.function.Function;


import com.stream.authentication.model.User;
import com.stream.authentication.service.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@Slf4j
public class JwtManager {


		//public static final long JWT_TOKEN_VALIDITY_IN_MS_EQUALS_TO_1_HOUR = 60;//4 * 60 * 60;/// MOVE IT PROPERTIES FILE


		@Autowired
		private KeyStoreUtil keyStoreUtil;
		@Value("${app.jwtExpirationMs}")
		private int jwtExpirationMs;//# 3600000 = JWT expiration time (1 hour in milliseconds)
		
		@Autowired
		public JwtManager(KeyStoreUtil keyStoreUtil) {
			super();
			this.keyStoreUtil = keyStoreUtil;
		}



		public String generateJwtTokenWithSubject(Map<String, Object> claims, String subject) {

			return Jwts.builder()
					.setClaims(claims)
					.setSubject(subject)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
					.signWith(keyStoreUtil.getRsaPrivateKey(),SignatureAlgorithm.RS256 )
					.compact();// Asymmetric encryption approach
		}
		public String generateToken(User user) {
			// User must be loggedin , check code
			Map<String, Object> claims = new HashMap<>();
			claims.put("id", user.getId());
			claims.put("email", user.getEmail());
			claims.put("username", user.getUsername());
			//claims.put("roles", user.getAuthorities());
			claims.put("roles", user.getRoles());// what is there are multiple role assigned to user
			return generateJwtTokenWithSubject(claims, user.getUsername());
		}

		




		public String generateJwtTokenWithAuthentication(Map<String, Object> claims, Authentication authentication) {
			CustomUserDetails userLoggedInPrincipal = (CustomUserDetails) authentication.getPrincipal();
			return Jwts.builder()
					.setClaims(claims)
					.setSubject((userLoggedInPrincipal.getUsername()))
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
					.signWith(keyStoreUtil.getRsaPrivateKey(),SignatureAlgorithm.RS256 )
					.compact();
		}
		

		// get username from token
		public String getUsernameFromToken(String token) {
			return getClaimFromToken(token, Claims::getSubject);
		}

		// get user id from token
		public String getIdFromToken(String token) {
			return getClaimFromToken(token, Claims::getId);
		}

		// get claim from token
		public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
			final Claims claims = getAllClaimsFromToken(token);
			
			System.out.println("claims JwtManager : " + claims);
			System.out.println("claims claimsResolver JwtManager: " + claimsResolver.apply(claims));
			return claimsResolver.apply(claims);
		}

		// get information from token
		public Claims getAllClaimsFromToken(String token) {
			//return Jwts.parser().setSigningKey(keystoreConfig.getJwtSigningPrivateKey()).parseClaimsJws(token).getBody();
			return Jwts.parserBuilder()
					.setSigningKey(keyStoreUtil.getRsaPrivateKey())
					.build().parseClaimsJws(token).getBody();
		}

	// validate token
		public Boolean validateToken(String token, UserDetails userDetails) {
			final String username = getUsernameFromToken(token);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}

		// check if token is expired
		public Boolean isTokenExpired(String token) {
			final Date expiration = getExpirationDateFromToken(token);
			return expiration.before(new Date());
		}
		// get expiration time from jwt token
		public Date getExpirationDateFromToken(String token) {
			return getClaimFromToken(token, Claims::getExpiration);
		}


}
/** It appears to be a method that checks whether a given authentication token has expired. Here’s an explanation:

 Method Signature:
 private Boolean isTokenExpired(String token)
 Functionality:
 The purpose of this method is to determine whether the provided token has expired.
 It does so by comparing the token’s expiration date (retrieved from the getExpirationDateFromToken method) with the current date.
 Steps Explained:
 final Date expiration = getExpirationDateFromToken(token);
 This line retrieves the expiration date from the token using the getExpirationDateFromToken method (which is not shown here).
 The expiration variable holds this date.
 return expiration.before(new Date());
 Here, it checks if the expiration date is before the current date (i.e., if the token has already expired).
 If the expiration date is earlier than the current date, the method returns true, indicating that the token is expired; otherwise, it returns false.
 * */

/** Let’s talk about asymmetric encryption (also known as public-key cryptography). It’s a fascinating topic that plays a crucial role in securing our digital communications. 🌟

 What is Asymmetric Encryption?
 Asymmetric encryption uses two different keys to safeguard information:
 Public Key: This key is openly distributed and used for encrypting data.
 Private Key: The private key is kept secret by the owner and is used by the receiver to decrypt data.
 The magic lies in the fact that data encrypted with the public key can only be decrypted with the corresponding private key. It’s like having a digital lock and key pair! 🔐
 How It Works:
 Imagine Alice wants to send a secure message to Bob. Here’s how they use asymmetric encryption:
 Key Generation:
 Bob generates a key pair: a public key and a private key.
 He shares the public key with Alice.
 Encryption:
 Alice takes Bob’s public key and encrypts her message.
 Only Bob’s private key can unlock this encrypted message.
 Decryption:
 Bob receives the encrypted message and uses his private key to decrypt it.
 Voilà! He can now read Alice’s secret message.
 Why It Matters:
 Asymmetric encryption is essential for:
 Secure Communication: Websites use it for HTTPS (TLS/SSL) to protect data during transmission.
 Digital Signatures: It ensures the authenticity and integrity of messages.
 Key Exchange: Establishing secure channels without sharing secrets beforehand.
 Comparison with Symmetric Encryption:
 In symmetric encryption, the same key is used for both encryption and decryption. It’s like having a single key for both locking and unlocking a box.
 Symmetric encryption is faster but faces challenges in securely sharing the key.
 Examples of Algorithms:
 Common asymmetric encryption algorithms include:
 RSA: Named after its inventors, Rivest, Shamir, and Adleman.
 Elliptic Curve Cryptography (ECC): Efficient and widely used.
 DSA (Digital Signature Algorithm): For digital signatures.
 Real-World Use Cases:
 When you visit a secure website (like online banking), asymmetric encryption ensures your data remains confidential.
 Digital certificates (like SSL/TLS certificates) rely on asymmetric keys to verify website authenticity.
 Remember, asymmetric encryption is like having a digital lock and key duo, ensuring that only the right recipient can unlock the secrets you send! 🗝️
 * */
