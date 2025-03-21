package com.example.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>
{
	List<Post> findByCategoryId(long categoryId);
}
