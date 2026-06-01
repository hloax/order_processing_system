package com.orderprocessing.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orderprocessing.dto.auth.AuthResponse;
import com.orderprocessing.dto.auth.LoginRequest;
import com.orderprocessing.dto.auth.RegisterRequest;
import com.orderprocessing.entity.Role;
import com.orderprocessing.entity.UserEntity;
import com.orderprocessing.repository.UserRepository;
import com.orderprocessing.security.JwtService;

//import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
		public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public AuthResponse register(RegisterRequest request) {
				
		UserEntity user = UserEntity.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(
						request.getPassword()))
				.role(Role.CUSTOMER)
				.build();
		
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email already registered");
		}
		
		userRepository.save(user);
		
		String token = jwtService.generateToken(user.getEmail());
		
		return AuthResponse.builder().token(token).build();
	}
	
	public AuthResponse login(LoginRequest request) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);
		
		String token = jwtService.generateToken(request.getEmail());
		
		return AuthResponse.builder().token(token).build();
	}
}
