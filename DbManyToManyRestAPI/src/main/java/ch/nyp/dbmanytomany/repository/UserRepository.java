package ch.nyp.dbmanytomany.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.nyp.dbmanytomany.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
