package ch.nyp.dbfirst.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.nyp.dbfirst.model.User;
import ch.nyp.dbfirst.repository.UserRepository;

@Controller   
@RequestMapping(path="/databaseFirstDemo") 
public class MainController {
	
	@Autowired 
	private UserRepository userRepository;

	@PostMapping(path="user/add") 
	public @ResponseBody String addNewUser(@RequestBody User user) {
		userRepository.save(user);
		return "User saved";
	}

	@GetMapping(path="user/all")
	public @ResponseBody List<User> getAllUsers() {
		List<User> usersFromDB = userRepository.findAll();
		return usersFromDB;
	}
}
