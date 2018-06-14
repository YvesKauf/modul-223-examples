package ch.nyp.dbmanytomany.dtoconverter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import ch.nyp.dbmanytomany.dto.GroupDto;
import ch.nyp.dbmanytomany.dto.UserDto;
import ch.nyp.dbmanytomany.model.Group;
import ch.nyp.dbmanytomany.model.User;

public class GroupDtoConverter {

	@Autowired
    private ModelMapper modelMapper;
	
	public GroupDto convertToDto(Group group) {
		GroupDto groupDto = modelMapper.map(group, GroupDto.class);
	    return groupDto;
	}
	
	public Group convertToEntity(GroupDto groupDto) {
		Group group = modelMapper.map(groupDto, Group.class);
	    return group;
	}
	
	public GroupDto convertToDtoRead(Group group) {
		GroupDto groupDto = new GroupDto(group.getId(), group.getName());
		
		List<User> users = group.getUsers();
		List<UserDto> userDtos = new ArrayList<UserDto>();
		for (User user : users) {
			UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
			userDtos.add(userDto);
		}
		groupDto.setUsers(userDtos);
		return groupDto;
	}
}
