package com.test.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.spring.exception.ResourceNotFoundException;

import com.test.spring.model.User;
import com.test.spring.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/workflow/")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/users/{username}")
	//@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		User user= userRepository.findByUsername(username)
				.orElseThrow(()-> new ResourceNotFoundException("user with username : "+username+" DOES'NT EXIST"));
		return ResponseEntity.ok(user);
	}
}
