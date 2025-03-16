package com.example.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Category;
import com.example.blog.entity.Post;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.PostDto;
import com.example.blog.payload.PostResponse;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService
{

	private PostRepository postRepository;
	private ModelMapper mapper;
	private CategoryRepository categoryRepository;
	
	public PostServiceImpl(PostRepository postRepository,ModelMapper mapper,CategoryRepository categoryRepository) 
	{	
		this.postRepository = postRepository;
		this.mapper = mapper;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		// TODO Auto-generated method stub
		//convert Dto to post
		Category category= categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
		Post post = convertToEntity(postDto);
		post.setCategory(category);
		Post postResponse = postRepository.save(post);
		PostDto responsePostDto = convertToDto(postResponse);
		
		return responsePostDto;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) 
	{
		// TODO Auto-generated method stub
		// creating Sort instance to sort if sortDir is asc else in desc order.
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():Sort.by(sortBy).descending(); 
		
		//Create pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> postPage = postRepository.findAll(pageable);
		
		//get content from page 
		List<Post> postList= postPage.getContent();
		
		List<PostDto> content = postList.stream().map(post->convertToDto(post)).collect(Collectors.toList());
		PostResponse response = new PostResponse();
		response.setContent(content);
		response.setPageNo(postPage.getNumber());
		response.setPageSize(postPage.getSize());
		response.setTotalPages(postPage.getTotalPages());
		response.setTotalElements(postPage.getTotalElements());
		response.setLast(postPage.isLast());
		
		return response;
		
	}

	@Override
	public PostDto getPostById(long id) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		PostDto postDto = convertToDto(post);
		return postDto;
	}
	
	@Override
	public PostDto updatePost(PostDto postDto, Long id) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		Category category= categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		PostDto updatedPostDto = convertToDto(post);
		return updatedPostDto;
	}
	
	@Override
	public void deletePostById(long id) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}
	
	//convert PostEntity to PostDto
		private PostDto convertToDto(Post post)
		{
			PostDto postDto = mapper.map(post, PostDto.class);
//			postDto.setId(post.getId());
//			postDto.setTitle(post.getTitle());
//			postDto.setDescription(post.getDescription());
//			postDto.setContent(post.getContent());
			return postDto;
		}
		
		private Post convertToEntity(PostDto postDto)
		{
			Post post = mapper.map(postDto, Post.class);
//			post.setTitle(postDto.getTitle());
//			post.setDescription(postDto.getDescription());
//			post.setContent(postDto.getContent());
//			
			return post;
		}

		@Override
		public List<PostDto> getPostByCategory(long categoryId) {
			// TODO Auto-generated method stub
			Category category = categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
			
			List<Post> posts = postRepository.findByCategoryId(categoryId);
			
			return posts.stream().map((post)-> convertToDto(post)).collect(Collectors.toList());
			
		}
}
