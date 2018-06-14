package ch.nyp.dbcustomrepo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ch.nyp.dbcustomrepo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
