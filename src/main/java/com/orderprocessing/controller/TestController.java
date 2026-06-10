package com.orderprocessing.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

	@GetMapping
	public String testing() {
		return "Congratulations on accessing the protected page";
	}
}
