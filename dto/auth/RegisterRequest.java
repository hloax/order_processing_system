package com.orderprocessing.dto.auth;

public class RegisterRequest {

	private String name;
	
	private String email;
	
	private String password;

	public RegisterRequest() {}
	
	public RegisterRequest(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public RegisterRequest(Builder builder) {
		this.name = builder.name;
		this.email = builder.email;
		this.password = builder.password;
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private String name;
		private String email;
		private String password;
		
		public Builder Name(String name) {
			this.name = name;
			return this;
		}

		public Builder Email(String email) {
			this.email = email;
			return this;
		}
		public Builder Password(String password) {
			this.password = password;
			return this;
		}
	}
}
