package ch.nyp.dbcustomrepo.dtoconverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import ch.nyp.dbcustomrepo.dto.BookDto;
import ch.nyp.dbcustomrepo.model.Book;
import ch.nyp.dbcustomrepo.model.User;


/**
 * Links:
 * http://www.baeldung.com/spring-data-repositories
 * 
 * @author holzer
 *
 */
public class BookDtoConverter {

	@Autowired
    private ModelMapper modelMapper;
	
	public BookDto convertToDto(Book book) {
		BookDto bookDto = modelMapper.map(book, BookDto.class);
	    return bookDto;
	}
	
	public Book convertToEntity(BookDto bookDto) {
		Book book = modelMapper.map(bookDto, Book.class);
		
		User author = new User();
		author.setId(bookDto.getIdauthor());
		book.setAuthor(author);
		
		User owner = new User();
		owner.setId(bookDto.getIdowner());
		book.setOwner(owner);
		
	    return book;
	}
}
