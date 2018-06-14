package ch.nyp.dbcustomrepo;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.nyp.dbcustomrepo.dtoconverter.BookDtoConverter;
import ch.nyp.dbcustomrepo.dtoconverter.UserDtoConverter;

@Configuration
public class SpringConfig {

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Bean
	public UserDtoConverter userDtoConverter() {
		return new UserDtoConverter();
	}
	
	@Bean
	public BookDtoConverter bookDtoConverter() {
		return new BookDtoConverter();
	}
}
