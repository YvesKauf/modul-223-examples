package ch.nyp.pendenzen_app.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.nyp.pendenzen_app.models.User;

/**
 * Created by Robin Furrer on 25.01.2018
 */
@Dao
public interface UserDao {

	/**
	 *
	 * needed for the login
	 * returns all information of this user
	 * or null if the user doesn't exists
	 *
	 * @param email
	 * @param password
	 * @return User
	 */
	@Query("SELECT userId, firstname, lastname, email, password, profilpicture, isApprentice FROM " +
			"User WHERE " +
			"email = :email AND password = :password")
	public User loadUser(String email, String password);

	/**
	 *
	 * checks Email address if exists
	 *
	 * needed for register or if email is correct in login
	 *
	 * @param email
	 * @return
	 */
	@Query("SELECT userId, firstname, lastname, email, password, profilpicture, isApprentice FROM User WHERE " +
			"email = :email")
	public User checkEmail(String email);

	@Query("SELECT * FROM User WHERE isApprentice = 1")
	public List<User> getAllApprentices();

	/**
	 * only needed for testing or debugging
	 */
	@Query("DELETE FROM User")
	void deleteAll();

	/**
	 *
	 * creates new User
	 *
	 * @param user
	 */
	@Insert
	void insert(User user);

	@Insert
	void insertAll(List<User> users);
}
