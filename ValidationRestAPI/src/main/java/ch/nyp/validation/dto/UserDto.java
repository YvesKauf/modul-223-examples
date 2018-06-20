package ch.nyp.validation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {

	private Long id;

	@NotNull
    @Size(min=2, message="name muss mindestens 2 Zeichen aufweisen.")
    private String name;

	@NotNull
	@Email(message="email muss ein korrektes Format aufweisen.")
    private String email;
    
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
}
