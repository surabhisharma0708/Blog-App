package com.example.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.blog.entity.Role;
import com.example.blog.repository.RoleRepository;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
			title="Spring Boot Blog App REST API's",
			description="Spring Boot Blog App REST API's Description",
			version = "v1.0",
			contact = @Contact(
					name="Surabhi",
					email="shailendrisharma2001@gmail.com",
					url="https://www.javaguides.net"
					),
			license = @License(
				name="Surabhi",
				url="https://www.javaguides.net/licenses"
				
				)
			),
		externalDocs = @ExternalDocumentation(
			description="Spring Boot Blog App REST API's Description",
			url="https://github.com"
		)
		)
public class SpringBootBlogAppApplication  implements CommandLineRunner
{
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception 
	{
		// TODO Auto-generated method stub
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);
		
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);
	}
}
