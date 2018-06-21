package ch.nyp.loginjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.nyp.loginjwt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}