package com.example.blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.payload.CommentDto;
import com.example.blog.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController 
{
	private CommentService commentService;
	
	public CommentController(CommentService commentService) 
	{
		this.commentService = commentService;
	}

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable long postId,@Valid @RequestBody CommentDto commentDto)
	{
		CommentDto commentDtoResponse = commentService.createComment(postId, commentDto);
		
		return new ResponseEntity(commentDtoResponse, HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable long postId)
	{
		return commentService.getCommentsByPostId(postId);
	}
	
	@GetMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value="postId") long postId, @PathVariable(value="id") long commentId)
	{
		return  new ResponseEntity<>(commentService.getCommentBtId(postId, commentId), HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateCommentById(@PathVariable(value="postId") long postId, @PathVariable(value="id") long commentId,@Valid @RequestBody CommentDto commentDto)
	{
		 return new ResponseEntity<>(commentService.updateCommentById(postId,commentId,commentDto), HttpStatus.OK);
	}
	
	@DeleteMapping("posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteCommentById(@PathVariable(value="postId") long postId, @PathVariable(value="id") long commentId)
	{
		 commentService.deleteCommentById(postId,commentId);
		 return ResponseEntity.ok("Deleted Comment Successfully");
	}
	
}
