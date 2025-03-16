package com.example.blog.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.blog.exception.BlogApiException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// This is a utility class for generating token, extracting username from token
@Component
public class JwtTokenProvider 
{
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;
	
	public String generateToken(Authentication authentication)
	{
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expirationDate =  new Date(currentDate.getTime() + jwtExpirationDate);
		
		String token = Jwts.builder()
				.subject(username)
				.issuedAt(currentDate)
				.expiration(expirationDate)
				.signWith(getKey())
				.compact();     //compact method will club all the info in one and create the token.
		
		return token;					
	}
	
	private Key getKey()
	{
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	//get username from the Token
	public String getUsername(String token)
	{
		return Jwts.parser()
			.verifyWith((SecretKey) getKey())
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();
	}
	
	public boolean validateToken(String token)
	{
		try 
		{
			Jwts.parser()
				.verifyWith((SecretKey) getKey())
				.build()
				.parse(token);
			return true;
		}
		catch(MalformedJwtException ex)
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
		}
		catch(ExpiredJwtException ex)
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT Token is expired");
		}
		catch(UnsupportedJwtException ex)
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token ");
		}
		catch(IllegalArgumentException ex)
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is null or empty");
		}
		
	}
}
