package ch.nyp.dbmanytomany.dto;

import java.util.List;

/**
 * http://javasampleapproach.com/json/resolve-json-infinite-recursion-problems-working-jackson
 * 
 * @author holzer
 */
public class UserDto {

	private Long id;

    private String name;

    private String email;
    
    private List<GroupDto> groups;
    
    public UserDto() {}
    
    public UserDto(Long id, String name, String email) {
    	this.id = id;
    	this.name = name;
    	this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<GroupDto> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupDto> groups) {
		this.groups = groups;
	}	
}
