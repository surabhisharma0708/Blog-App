package com.example.blog.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Role;
import com.example.blog.entity.User;
import com.example.blog.exception.BlogApiException;
import com.example.blog.payload.LoginDto;
import com.example.blog.payload.RegisterDto;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.security.JwtTokenProvider;
import com.example.blog.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService
{
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	
	public AuthServiceImpl(AuthenticationManager authenticationManager,UserRepository userRepository,
	 RoleRepository roleRepository,
	 PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository =userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) 
	{
		// TODO Auto-generated method stub
		Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
		
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {
		// TODO Auto-generated method stub
		if(userRepository.existsByUsername(registerDto.getUsername()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username already exists");
		}
		
		if(userRepository.existsByEmail(registerDto.getEmail()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email already exists");
		}
		
		User user = new User();
		user.setEmail(registerDto.getEmail());
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setPassword(registerDto.getPassword());
		
		Set<Role> roles = new HashSet<>();
		Role role = roleRepository.findByName("ROLE_USER").get();
		roles.add(role);
		user.setRoles(roles);
		
		return "User registered successfully";
	}
}
