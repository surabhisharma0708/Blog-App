package com.example.blog.service;

import com.example.blog.payload.LoginDto;
import com.example.blog.payload.RegisterDto;

public interface AuthService 
{
	public String login(LoginDto loginDto);
	
	public String register(RegisterDto registerDto);
}
