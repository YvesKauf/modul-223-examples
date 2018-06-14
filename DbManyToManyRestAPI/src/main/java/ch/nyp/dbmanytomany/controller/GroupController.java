package ch.nyp.dbmanytomany.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.nyp.dbmanytomany.dto.GroupDto;
import ch.nyp.dbmanytomany.dtoconverter.GroupDtoConverter;
import ch.nyp.dbmanytomany.model.Group;
import ch.nyp.dbmanytomany.repository.GroupRepository;

@Controller   
@RequestMapping(path="/mnDatabaseDemo") 
public class GroupController {

	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private GroupDtoConverter groupDtoConverter;
	
	@PostMapping(path="group/add") 
	public @ResponseBody String addNewGroup(@RequestBody GroupDto group) {
		
		Group dbGroup = groupDtoConverter.convertToEntity(group);
		groupRepository.save(dbGroup);
		return "Group saved";
	}
	
	@GetMapping(path="group/all")
	public @ResponseBody Iterable<GroupDto> getAllGroups() {
		List<Group> groupsFromDB = groupRepository.findAll();
		
		List<GroupDto> groups = groupsFromDB.stream()
		          .map(group -> groupDtoConverter.convertToDtoRead(group))
		          .collect(Collectors.toList());
		
		return groups;
	}
}
