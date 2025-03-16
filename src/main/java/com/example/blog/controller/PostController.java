package com.example.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;
import com.example.blog.service.PostService;
import com.example.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/posts")
@Tag(
	name="CRUD REST APIs for Post Resource"	
		)

public class PostController 
{
	
	private PostService postService;
	
	public PostController(PostService postService) 
	{
		this.postService = postService;
	}
	
	@Operation(
			summary = "Create REST POST API",
			description = "Create POST REST Api is used to save Post in database"
			)
	@ApiResponse(
			responseCode = "201",
			description = "Http status 201 created"
			)
	
	@SecurityRequirement(
			name="Bearer Authorization"
			)
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto)
	{
		 PostDto postDtoResponse= postService.createPost(postDto);
		return new ResponseEntity<>(postDtoResponse,HttpStatus.CREATED);
	}
	
	
	@Operation(
			summary = "GET ALL Posts REST API",
			description = "This is used to fetch all Posts from the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status 200 SUCCESS"
			)
	
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value="pageNo", defaultValue =AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
			@RequestParam(value="pageSize", defaultValue =AppConstants.DEFAULT_PAGE_Size,required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
	{
		return postService.getAllPosts(pageNo, pageSize,sortBy, sortDir);
	}
	
	
	@Operation(
			summary = "GET Post by Id REST API",
			description = "It is used to get single post from the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status 200 SUCCESS"
			)
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable long id)
	{
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	
	
	@Operation(
			summary = "Update Post REST API",
			description = "It is used to update a particular post in the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status 200 SUCCESS"
			)
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto , @PathVariable long id)
	{
		return ResponseEntity.ok(postService.updatePost(postDto,id));
	}
	
	
	@Operation(
			summary = "DELETE Post  REST API",
			description = "It is used to delete a post from the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status 200 SUCCESS"
			)
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable long id)
	{
		postService.deletePostById(id);
		return ResponseEntity.ok("Post Deleted Successfully");	
	}

	@Operation(
			summary = "GET Post by Category REST API",
			description = "It is used to get all the posts by category from the database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "HTTP status 200 SUCCESS"
			)
	
	
	@GetMapping("category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable long categoryId)
	{
		List<PostDto> postDtoResponse = postService.getPostByCategory(categoryId);
		return ResponseEntity.ok(postDtoResponse);
	}
}
