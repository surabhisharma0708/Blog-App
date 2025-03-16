package com.example.blog.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// We execute this before Spring Security Filters
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

	private JwtTokenProvider jwtTokenProvider;
	
	private UserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) 
	{
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
		//getting token from the http request
		String token = getTokenFromRequest(request);
		
		//validateToken and get Username From Token
		
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token))
		{
			//get the username from the token
			String username = jwtTokenProvider.getUsername(token);
			
			//load the user from the database related to this token
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			
			// create instance of UsernamePasswordAuthenticationToken
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
					(userDetails,null,userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)) ;
			
			//pass the authenticationToken to securityContectHolder
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);
	}
	
	private String getTokenFromRequest(HttpServletRequest request)
	{
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
		{
			return bearerToken.substring(7,bearerToken.length());
		}
		return null;
	}
}
