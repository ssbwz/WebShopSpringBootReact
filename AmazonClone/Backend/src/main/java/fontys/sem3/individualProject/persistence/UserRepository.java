package fontys.sem3.individualProject.persistence;

import fontys.sem3.individualProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
