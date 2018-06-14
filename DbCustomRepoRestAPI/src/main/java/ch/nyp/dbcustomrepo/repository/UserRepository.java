package ch.nyp.dbcustomrepo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ch.nyp.dbcustomrepo.model.User;

/**
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories 
 * 
 * Mit Derived Queries ist es möglich alleine durch die Methodenbenennung das gewünschte Query auszuführen.
 * 
 * 
 * @author holzer
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	//Derived Query
	public List<User> findByName(String name); 
	
	//Derived Query
	public List<User> findByEmail(String email);
	
	//Derived Query
	public List<User> findByNameAndEmail(String name, String email);
	
	//Derived Query
	public List<User> findByNameOrEmail(String name, String email);
	
	//Named Query
	@Query("select u from User u where u.name = ?1")
	public List<User> findByNameNamed(String name);
	
	//Named Query
	@Query("select u from User u where u.name = ?1 and u.email = ?2")
	public List<User> findByNameAndEmailNamed(String name, String email);
}
