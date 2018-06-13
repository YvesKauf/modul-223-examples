package ch.nyp.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.nyp.database.model.Book;


public interface BookRepository extends JpaRepository<Book, Long> {

}
