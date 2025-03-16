package com.example.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.entity.Role;
import com.example.blog.entity.User;

public interface RoleRepository extends JpaRepository<Role, Long>
{
	Optional<Role> findByName(String name);	
}
