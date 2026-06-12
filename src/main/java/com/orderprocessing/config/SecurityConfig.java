package com.orderprocessing.config;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.orderprocessing.security.JwtFilter;

@Configuration
public class SecurityConfig {

	private final JwtFilter jwtFilter;
	
	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http
			) throws Exception {
		
		http.csrf(csrf -> csrf.disable())
			.sessionManagement(session ->
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
					
					.requestMatchers("/api/auth/**")
					.permitAll()
					
					.requestMatchers(HttpMethod.GET, "/api/products/**")
					.authenticated()
					
					.requestMatchers(HttpMethod.POST, "/api/products/**")
					.hasAuthority("ADMIN")
					
					.requestMatchers(HttpMethod.PUT, "/api/products/**")
					.hasAuthority("ADMIN")
					
					.requestMatchers(HttpMethod.DELETE, "/api/products/**")
					.hasAuthority("ADMIN")
					
					.requestMatchers("/api/orders/**")
					.authenticated()
					
					.requestMatchers(HttpMethod.PATCH, "/api/orders/*/status")
					.hasAuthority("ADMIN")
					
					.requestMatchers(HttpMethod.PATCH, "/api/products/*/restock")
					.hasAuthority("ADMIN")
					
					.requestMatchers("/api/reports/**")
					.hasAuthority("ADMIN")
					
					.anyRequest()
					.authenticated()
			)
			.addFilterBefore(jwtFilter,
					UsernamePasswordAuthenticationFilter.class
			);
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config
			) throws Exception {
		
		return config.getAuthenticationManager();
	}
}
