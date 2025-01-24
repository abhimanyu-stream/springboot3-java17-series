package com.stream.authentication.filter;

import java.io.IOException;
import java.util.Map.Entry;


import com.stream.authentication.configuration.JwtManager;
import com.stream.authentication.service.CustomUserDetails;
import com.stream.authentication.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenSecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtManager jwtManager;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// get only token if the token is in the form of "Bearer token"
		/*if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {

			// Skip It first time login attempt, since very first when User is trying to login and will get JWT there before there is no JWT or Authorization Header can not sent

			 throw new TokenException(requestTokenHeader, "Request do not contains Authorization Header || It do not starts witb Beaner ");

		}*/
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

			jwtToken = requestTokenHeader.substring(7);

			try {
				username = jwtManager.getUsernameFromToken(jwtToken);
				System.out.println("username inside doFilterInternal() " + username);
			} catch (IllegalArgumentException e) {
				logger.info("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				logger.info("JWT Token has expired");
			}
		}

		// validate the token
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// as username is null so we can now retrieve User object related to this username from DB
			CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);// load User object from DB by username, which contains Password too in that row
			
			System.out.println("username inside doFilterInternal() " + userDetails.getEmail());
			System.out.println("username inside doFilterInternal() " + userDetails.getId());
			System.out.println("username inside doFilterInternal() " + userDetails.getPassword());
			System.out.println("username inside doFilterInternal() " + userDetails.getUsername());
			System.out.println("username inside doFilterInternal() " + userDetails.getAuthorities());

			// if token is valid configure Spring Security to manually set authentication
			if (Boolean.TRUE.equals(jwtManager.validateToken(jwtToken, userDetails))) {
				
				
				//EXTRA READING TASK FROM TOKEN START
				Claims allClaims = jwtManager.getAllClaimsFromToken(jwtToken);
				
				for (Entry<String, Object> entry : allClaims.entrySet()) {
		            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		    	}
				//EXTRA READING TASK FROM TOKEN END

				//UsernamePasswordAuthenticationToken(Object principal, Object credentials)
				//This constructor can be safely used by any code that wishes to create a UsernamePasswordAuthenticationToken, as the AbstractAuthenticationToken.isAuthenticated() will return false.
				//UsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities)
				//This constructor should only be used by AuthenticationManager or AuthenticationProvider implementations that are satisfied with producing a trusted (i.e.
				
				Object[] credentials = new Object[] {userDetails.getUsername(), userDetails.getEmail(), userDetails.getPassword(), userDetails.getSerialversionuid()};
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, credentials, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}