package com.example.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.exception.BlogApiException;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.CommentDto;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	private CommentRepository commentRepository;
	
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) 
	{
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) 
	{
		// TODO Auto-generated method stub
		Comment comment = convertToEntity(commentDto);
		
		Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		comment.setPost(post);
		Comment responseComment=commentRepository.save(comment);
		CommentDto responseCommentDto = convertToDto(responseComment);
		
		return responseCommentDto;
	}
	
	@Override
	public List<CommentDto> getCommentsByPostId(long postId) 
	{
		// TODO Auto-generated method stub
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		//converting entity to Dto and returning commentDto
		return comments.stream().map(comment->convertToDto(comment)).collect(Collectors.toList());
		
	}

	@Override
	public CommentDto getCommentBtId(long postId, long commentId) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
		
		if(!comment.getPost().getId().equals(post.getId()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
		}
		
		return convertToDto(comment);
	}

	@Override
	public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
		
		if(!comment.getPost().getId().equals(post.getId()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
		}
		
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		Comment updatedComment = commentRepository.save(comment);
		return convertToDto(updatedComment);
	}

	@Override
	public void deleteCommentById(long postId, long commentId) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","commentId",commentId));
		
		if(!comment.getPost().getId().equals(post.getId()))
		{
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
		}
		commentRepository.delete(comment);
	}
	
//convert DTO to Entity
	
	private Comment convertToEntity(CommentDto commentDto)
	{
		Comment comment = mapper.map(commentDto, Comment.class);
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
		return comment;
	}
	
//	convert Entity to DTO
	
	private CommentDto convertToDto(Comment comment)
	{
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
//		
		return commentDto;
	}

}
