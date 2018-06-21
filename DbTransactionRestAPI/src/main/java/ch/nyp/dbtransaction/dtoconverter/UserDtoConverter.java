package ch.nyp.dbtransaction.dtoconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import ch.nyp.dbtransaction.dto.UserDto;
import ch.nyp.dbtransaction.model.User;


public class UserDtoConverter {

	@Autowired
    private ModelMapper modelMapper;
	
	public UserDto convertToDto(User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);
	    return userDto;
	}
	
	public User convertToEntity(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
	    return user;
	}
}