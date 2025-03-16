package com.example.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.payload.JwtAuthResponse;
import com.example.blog.payload.LoginDto;
import com.example.blog.payload.RegisterDto;
import com.example.blog.service.impl.AuthServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
	private AuthServiceImpl authServiceImpl;

	public AuthController(AuthServiceImpl authServiceImpl) 
	{
		this.authServiceImpl = authServiceImpl;
	}
	
	@PostMapping(value={"/login","/signin"})
	public ResponseEntity<JwtAuthResponse> Login(@RequestBody LoginDto loginDto)
	{
		String token = authServiceImpl.login(loginDto);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setAuthResponse(token);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping(value= {"/register","/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto)
	{
		return new ResponseEntity<String>(authServiceImpl.register(registerDto),HttpStatus.CREATED);
	}
} 
