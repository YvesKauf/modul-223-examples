package ch.nyp.database.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


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
	
	private String name;
	
	private String isbn;
	
	@JsonProperty(access = Access.WRITE_ONLY) //Feld wird im JSON nur beim Schreiben (POST) akzeptiert, d.h. im GET nicht zurückgegeben.
	private Long idowner;
	
	private UserDto owner;
	
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
