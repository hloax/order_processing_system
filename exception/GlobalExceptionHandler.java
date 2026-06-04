package com.orderprocessing.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.orderprocessing.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse>
			handleProductNotFound(ProductNotFoundException ex) {
		
		ErrorResponse response =
				new ErrorResponse(
						ex.getMessage(),
						HttpStatus.NOT_FOUND.value(),
						LocalDateTime.now());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse>
			handleEmailExists(EmailAlreadyExistsException ex) {
		
		ErrorResponse response =
				new ErrorResponse(
						ex.getMessage(),
						HttpStatus.BAD_REQUEST.value(),
						LocalDateTime.now());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse>
			handleGeneric(Exception ex) {
		
		ErrorResponse response =
				new ErrorResponse(
						ex.getMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR.value(),
						LocalDateTime.now());
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse>
			handleValidation(MethodArgumentNotValidException ex) {
		
		String field = ex.getBindingResult()
				.getFieldError()
				.getField();
		
		String message = ex.getBindingResult()
				.getFieldError()
				.getDefaultMessage();
		
		ErrorResponse response =
				new ErrorResponse(
						field + ": " + message,
						HttpStatus.BAD_REQUEST.value(),
						LocalDateTime.now());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<ErrorResponse>
			handleStockException(InsufficientStockException ex) {
		
		ErrorResponse response =
				new ErrorResponse(
						ex.getMessage(),
						HttpStatus.BAD_REQUEST.value(),
						LocalDateTime.now());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorResponse>
			handleOrderNotFound(OrderNotFoundException ex) {
		
		ErrorResponse response =
				new ErrorResponse(
						ex.getMessage(),
						HttpStatus.NOT_FOUND.value(),
						LocalDateTime.now());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidOrderStatusException.class)
	public ResponseEntity<ErrorResponse>
			handleInvalidStatus(InvalidOrderStatusException ex) {
		
		ErrorResponse response =
				new ErrorResponse(
						ex.getMessage(),
						HttpStatus.BAD_REQUEST.value(),
						LocalDateTime.now());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
