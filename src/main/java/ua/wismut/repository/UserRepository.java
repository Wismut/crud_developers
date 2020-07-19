package ua.wismut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.wismut.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
