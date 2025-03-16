package com.example.blog.service;

import java.util.List;

import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;

public interface PostService 
{
	 PostDto createPost(PostDto postDto);

	 PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

	 PostDto getPostById(long id);

	 PostDto updatePost(PostDto postDto, Long id);

	 void deletePostById(long id);
	 
	 List<PostDto> getPostByCategory(long categoryId);
}