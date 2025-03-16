package com.example.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.blog.entity.Category;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.payload.CategoryDto;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService
{
	private ModelMapper mapper;
	private CategoryRepository repository;
	
	public CategoryServiceImpl(ModelMapper mapper, CategoryRepository repository) 
	{
		this.mapper = mapper;
		this.repository = repository;
	}

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) 
	{
		// TODO Auto-generated method stub
		Category category= convertToEntity(categoryDto);
		repository.save(category);
		CategoryDto categoryDtoResponse= convertToDto(category);
		return categoryDtoResponse;
	}
	
	@Override
	public CategoryDto getCategoryById(Long categoryId)
	{
		Category category = repository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		return convertToDto(category);
	}
	
	@Override
	public List<CategoryDto> getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> listOfCategories = repository.findAll();
		
		return listOfCategories.stream().map(category-> 
			convertToDto(category))
				.collect(Collectors.toList());
		
	}
	
	private Category convertToEntity(CategoryDto categoryDto)
	{
		return mapper.map(categoryDto, Category.class);
	}
	private CategoryDto convertToDto(Category category)
	{
		return mapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		
		Category category = repository.findById(categoryId)
		.orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setId(categoryDto.getId());
		
		repository.save(category);
		CategoryDto updatedCategoryDto = convertToDto(category);
		return updatedCategoryDto;
	}

	@Override
	public void deleteCategory(Long categoryId) 
	{
		// TODO Auto-generated method stub
		Category category = repository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		repository.delete(category);
	}

	

}
