package com.example.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>
{
	
}
