package com.example.blog.service;

import java.util.List;

import com.example.blog.payload.CategoryDto;

public interface CategoryService 
{
	CategoryDto addCategory(CategoryDto categoryDto);
	
	CategoryDto getCategoryById(Long categoryId);
	
	List<CategoryDto> getAllCategories();
	
	CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
	
	void deleteCategory(Long categoryId);
}
