package ch.nyp.dbtransaction;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.nyp.dbtransaction.dtoconverter.UserDtoConverter;


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
}
