package ch.nyp.dbmanytomany;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.nyp.dbmanytomany.dtoconverter.GroupDtoConverter;
import ch.nyp.dbmanytomany.dtoconverter.UserDtoConverter;


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
	public GroupDtoConverter groupDtoConverter() {
		return new GroupDtoConverter();
	}
}
