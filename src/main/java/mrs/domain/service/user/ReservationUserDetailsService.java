package mrs.domain.service.user;

import mrs.domain.model.User;
import mrs.domain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ReservationUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findById(userName).orElse(null);
		if (user == null) {
			throw new UsernameNotFoundException(userName + " is not found.");
		}
		return new ReservationUserDetails(user);
	}
}