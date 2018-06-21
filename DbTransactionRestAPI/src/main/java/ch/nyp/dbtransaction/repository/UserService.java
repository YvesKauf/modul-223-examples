package ch.nyp.dbtransaction.repository;

import java.util.List;

import ch.nyp.dbtransaction.model.User;

public interface UserService {

	void saveUsers(List<User> users);
}
