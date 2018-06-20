package ch.nyp.validation.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ch.nyp.validation.validator.UserId;


/**
 * Links:
 * http://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
 * https://stackoverflow.com/questions/36174516/rest-api-dtos-or-not
 * 
 * @author holzer
 *
 */
public class BookDto {

    private Long id;
	
    @NotNull
    @Size(min=2, message="name muss mindestens 2 Zeichen aufweisen.")
	private String name;
    
    @NotNull
    @Size(min=1, message="isbn darf nicht leer sein.")
	private String isbn;
	
    @NotNull
    @Min(value = 1, message="idowner muss >= 1 sein.")
	@JsonProperty(access = Access.WRITE_ONLY) //Feld wird im JSON nur beim Schreiben (POST) akzeptiert, d.h. im GET nicht zurückgegeben.
	private Long idowner;
	
	private UserDto owner;
	
	@UserId(message="idauthor ist ungültig. Muss > 10 sein.")
	@JsonProperty(access = Access.WRITE_ONLY) //Feld wird im JSON nur beim Schreiben (POST) akzeptiert, d.h. im GET nicht zurückgegeben.
	private Long idauthor;
	
	private UserDto author;

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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Long getIdowner() {
		return idowner;
	}

	public void setIdowner(Long idowner) {
		this.idowner = idowner;
	}

	public UserDto getOwner() {
		return owner;
	}

	public void setOwner(UserDto owner) {
		this.owner = owner;
	}

	public Long getIdauthor() {
		return idauthor;
	}

	public void setIdauthor(Long idauthor) {
		this.idauthor = idauthor;
	}

	public UserDto getAuthor() {
		return author;
	}

	public void setAuthor(UserDto author) {
		this.author = author;
	}
}
