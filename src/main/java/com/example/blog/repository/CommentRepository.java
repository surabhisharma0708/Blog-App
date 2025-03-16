package com.example.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.Comment;
import com.example.blog.payload.CommentDto;

public interface CommentRepository extends JpaRepository<Comment, Long>
{

	List<Comment> findByPostId(long postId);

}
