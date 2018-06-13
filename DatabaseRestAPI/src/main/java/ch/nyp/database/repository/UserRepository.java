package ch.nyp.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.nyp.database.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
