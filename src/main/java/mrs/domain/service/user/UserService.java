package mrs.domain.service.user;

import java.util.List;

import mrs.domain.exception.AlreadyRegisteredReservationsException;
import mrs.domain.model.Reservation;
import mrs.domain.model.User;
import mrs.domain.repository.reservation.ReservationRepository;
import mrs.domain.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	public List<User> findUsers() {
		return userRepository.findAll(Sort.by(Sort.Direction.ASC, "UserId"));
	}
	
	public User findUser(String userId) {
		return userRepository.findById(userId).orElse(new User());
	}
	
	public void regist(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public void update(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public void delete(String userId) {
		// 予約データの存在確認
		Reservation reservation = new Reservation();
		User user = new User();
		user.setUserId(userId);
		reservation.setUser(user);
		Example<Reservation> example = Example.of(reservation);
		long reservedCount = reservationRepository.count(example);
		if (reservedCount > 0) {
			throw new AlreadyRegisteredReservationsException("削除対象ユーザで登録されている予約データがあります。");
		}
		userRepository.deleteById(userId);
	}
}