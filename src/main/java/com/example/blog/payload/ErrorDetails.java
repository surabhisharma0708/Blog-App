package com.example.blog.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDetails 
{
	private Date timestamp;
	private String message;
	private String details;
	
	
}
