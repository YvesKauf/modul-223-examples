package ch.nyp.loginjwt.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.nyp.loginjwt.repository.UserRepository;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
        ch.nyp.loginjwt.model.User user = (ch.nyp.loginjwt.model.User) userRepository.findByEmail(email);
        
        if (user == null) {
            throw new UsernameNotFoundException(user.getEmail());
        }
        return new User("" + user.getId(), user.getPassword(), emptyList());
    }
}