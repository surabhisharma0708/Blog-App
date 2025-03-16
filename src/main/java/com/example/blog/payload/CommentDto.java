package com.example.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto 
{
	private Long id;
	
	@NotEmpty(message="Name should not be null or empty")
	private String name;
	@NotEmpty(message="Email should not be null or empty")
	@Email
	private String email;
	@NotEmpty(message="Body should not be null or empty")
	@Size(min=10,message="Body should have atleast 10 characters")
	private String body;
}
