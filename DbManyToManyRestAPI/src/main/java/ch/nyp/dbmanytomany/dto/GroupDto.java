package ch.nyp.dbmanytomany.dto;

import java.util.List;

public class GroupDto {

	private Long id;

    private String name;
    
    private List<UserDto> users;
    
    public GroupDto() {}
    
    public GroupDto(Long id, String name) {
    	this.id = id;
    	this.name = name;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}
}
