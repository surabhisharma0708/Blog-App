package com.example.blog.service;

import java.util.List;

import org.springframework.http.HttpStatusCode;

import com.example.blog.payload.CommentDto;


public interface CommentService 
{
	public CommentDto createComment(long postId, CommentDto commentDto);

	public List<CommentDto> getCommentsByPostId(long postId);

	public CommentDto getCommentBtId(long postId, long commentId);

	public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto);

	public void deleteCommentById(long postId, long commentId);
	
}
