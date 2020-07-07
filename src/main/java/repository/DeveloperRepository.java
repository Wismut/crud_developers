package repository;

import model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    List<Developer> findAllBySpecialtyName(String specialityName);
}
