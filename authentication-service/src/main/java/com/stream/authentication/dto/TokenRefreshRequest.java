package com.stream.authentication.dto;



import java.io.Serializable;


public class TokenRefreshRequest implements Serializable {

	private static final long serialVersionUID = 3577323907495563008L;

	private String refreshToken;


	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
}
