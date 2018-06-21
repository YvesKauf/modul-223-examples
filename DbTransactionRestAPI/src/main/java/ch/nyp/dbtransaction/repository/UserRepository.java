package ch.nyp.dbtransaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.nyp.dbtransaction.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
