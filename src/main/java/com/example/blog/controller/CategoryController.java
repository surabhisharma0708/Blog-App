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
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.payload.CategoryDto;
import com.example.blog.service.CategoryService;
import com.example.blog.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("api/category")
public class CategoryController 
{
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto)
	{
		return new ResponseEntity<>(categoryService.addCategory(categoryDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name="id") Long categoryId)
	{
		return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories()
	{
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> upadteCategory(@PathVariable("id") Long categoryId, @RequestBody CategoryDto categoryDto)
	{
		return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDto));
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId)
	{
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category Delete Successfully");
	}
}