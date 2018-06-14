package ch.nyp.dbcustomrepo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Links: 
 * https://stackoverflow.com/questions/12505141/only-using-jsonignore-during-serialization-but-not-deserialization
 * https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/
 * http://www.baeldung.com/spring-data-rest-relationships
 * 
 * @author holzer
 *
 */
@Entity 
public class Book {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	private String name;
	
	private String isbn;
	
	@ManyToOne
	@JoinColumn(name = "idowner", nullable = false)
	private User owner;

	@ManyToOne
	@JoinColumn(name = "idauthor", nullable = false)
	private User author;

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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
