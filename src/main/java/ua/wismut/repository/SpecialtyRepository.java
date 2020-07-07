package ua.wismut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.wismut.model.Specialty;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

}
