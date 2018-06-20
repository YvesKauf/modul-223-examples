package ch.nyp.validation.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import ch.nyp.validation.dto.BookDto;
import ch.nyp.validation.dto.UserDto;

/**
 * Links:
 * http://www.springboottutorial.com/spring-boot-validation-for-rest-services
 * https://softwarecave.org/2014/03/27/custom-bean-validation-constraints/
 * 
 * @author holzer
 */
@Controller    
@Validated
@RequestMapping(path="/validationDemo") 
public class MainController {

	@PostMapping(path="user/add") 
	public @ResponseBody String addNewUser(@Valid @RequestBody UserDto userDto) {
		return "User saved";
	}
	
	@PostMapping(path="book/add") 
	public @ResponseBody String addNewBook(@Valid @RequestBody BookDto book) {
		return "Book saved";
	}
	
	@GetMapping("book/{id}")
	public @ResponseBody BookDto getBookById(@PathVariable 
			@Min(value=10, message="Id muss grösser als 9 sein.") long id) {
		BookDto book = new BookDto();
		book.setId(10L);
		book.setName("Testbuch");
		book.setIsbn("sddlas");
		return book;
	}
}
