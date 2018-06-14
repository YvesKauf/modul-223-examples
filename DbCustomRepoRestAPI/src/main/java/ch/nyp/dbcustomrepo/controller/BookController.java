package ch.nyp.dbcustomrepo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.nyp.dbcustomrepo.dto.BookDto;
import ch.nyp.dbcustomrepo.dtoconverter.BookDtoConverter;
import ch.nyp.dbcustomrepo.model.Book;
import ch.nyp.dbcustomrepo.repository.BookRepository;

@Controller
@RequestMapping(path = "/dbcustomrepo")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookDtoConverter bookDtoConverter;

	@PostMapping(path = "book/add")
	public @ResponseBody String addNewBook(@RequestBody BookDto book) {

		Book dbBook = bookDtoConverter.convertToEntity(book);
		bookRepository.save(dbBook);
		return "Book saved";
	}

	@GetMapping(path = "book/all")
	public @ResponseBody Iterable<BookDto> getAllBooks() {
		List<Book> booksFromDB = bookRepository.findAll();

		List<BookDto> books = booksFromDB.stream().map(book -> bookDtoConverter.convertToDto(book))
				.collect(Collectors.toList());

		return books;
	}
}
