package ch.nyp.dbmanytomany.dtoconverter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import ch.nyp.dbmanytomany.dto.GroupDto;
import ch.nyp.dbmanytomany.dto.UserDto;
import ch.nyp.dbmanytomany.model.Group;
import ch.nyp.dbmanytomany.model.User;

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
	
	public UserDto convertToDtoRead(User user) {
		UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
		
		List<Group> userGroups = user.getGroups();
		List<GroupDto> userDtoGroups = new ArrayList<GroupDto>();
		for (Group group : userGroups) {
			GroupDto groupDto = new GroupDto(group.getId(), group.getName());
			userDtoGroups.add(groupDto);
		}
		userDto.setGroups(userDtoGroups);
		return userDto;
	}
}