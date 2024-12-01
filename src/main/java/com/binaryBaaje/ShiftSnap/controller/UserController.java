package com.binaryBaaje.ShiftSnap.controller;

import java.lang.annotation.Repeatable;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.binaryBaaje.ShiftSnap.exception.UserNotFoundException;
import com.binaryBaaje.ShiftSnap.jpa.UserRepository;
import com.binaryBaaje.ShiftSnap.model.User;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class UserController {
	
	private UserRepository userRepository;
	
	public UserController( UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> retrieveAllUser(){
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/users/{userId}")
	public Optional<User> retrieveUser(@PathVariable Long userId) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("userId: "+ userId);
		}
		
		return user;
		 
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> addNewUser(@Valid @RequestBody User user) {
		User savedUser =userRepository.save(user);
		
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
		
	
		
	}

	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody  User user){

		User existingUser = userRepository.findByEmail(user.getEmail());
		if(existingUser != null && user.getPassword().equals(existingUser.getPassword())){
			return new ResponseEntity<>(existingUser, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		

	}
	
	


	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()){
			userRepository.deleteById(userId);
			return new ResponseEntity<>("Deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		
	}

	@PutMapping("/users/{userId}")
	public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, @PathVariable Long userId) {
		if(userRepository.existsById(userId)){
			user.setUserId(userId);
			userRepository.save(user);
			return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
	}
}
