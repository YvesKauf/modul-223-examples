package ch.nyp.dbtransaction.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.nyp.dbtransaction.model.User;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Transactional()
	@Override
	public void saveUsers(List<User> users) {
		for (User user : users) {
			userRepository.save(user);
		}
	}
}
