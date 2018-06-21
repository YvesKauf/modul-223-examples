package ch.nyp.dbtransaction.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.nyp.dbtransaction.dto.UserDto;
import ch.nyp.dbtransaction.dtoconverter.UserDtoConverter;
import ch.nyp.dbtransaction.model.User;
import ch.nyp.dbtransaction.repository.UserRepository;
import ch.nyp.dbtransaction.repository.UserService;

/**
 * 
 * @author holzer
 *
 */
@Controller   
@RequestMapping(path="/transactionDemo") 
public class MainController {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private UserDtoConverter userDtoConverter;
	
	@Autowired
	private UserService userService;


	@PostMapping(path="user/addmulti") 
	public @ResponseBody String addNewUsers(@RequestBody List<UserDto> userDtos) {
		List<User> users = userDtos.stream()
		          .map(user -> userDtoConverter.convertToEntity(user))
		          .collect(Collectors.toList());
		
		userService.saveUsers(users);
		return "Users saved";
	}

	@GetMapping(path="user/all")
	public @ResponseBody List<UserDto> getAllUsers() {
		// This returns a JSON or XML with the users
		List<User> usersFromDB = userRepository.findAll();
		
		List<UserDto> users = usersFromDB.stream()
		          .map(user -> userDtoConverter.convertToDto(user))
		          .collect(Collectors.toList());
		
		return users;
	}
}
