package ua.wismut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.wismut.model.Developer;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    List<Developer> findAllBySpecialtyName(String specialityName);
}
