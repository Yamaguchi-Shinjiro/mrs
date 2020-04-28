package mrs.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mrs.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}