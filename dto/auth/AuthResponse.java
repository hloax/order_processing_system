package com.orderprocessing.dto.auth;

public class AuthResponse {
	private String token;

	public AuthResponse(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public AuthResponse(Builder builder) {
		this.token = builder.token;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	//Nested Static Builder Class
	public static class Builder {
		private String token;
		
		public Builder token(String token) {
			this.token = token;
			return this;
		}
	
		public AuthResponse build() {
			return new AuthResponse(this);
		}
	}
}
