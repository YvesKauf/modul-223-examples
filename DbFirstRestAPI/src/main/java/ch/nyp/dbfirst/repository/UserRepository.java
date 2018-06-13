package ch.nyp.dbfirst.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.nyp.dbfirst.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
