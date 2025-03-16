package com.example.blog.payload;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto 
{
	private Long id;
	
	// title should not be null and have atleast 2 characters
	@NotEmpty
	@Size(min=2,message="Post title sould have atleast 2 characters")
	private String title;
	
	// description should not be null or empty and have atleast 10 characters
	@NotEmpty
	@Size(min=10, message="Description title sould have atleast 10 characters")
	private String description;
	
	@NotEmpty
	private String content;
	private Set<CommentDto> comments;
	
	private long categoryId;
}
