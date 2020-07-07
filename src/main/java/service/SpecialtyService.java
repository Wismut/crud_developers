package service;

import model.Specialty;

import java.util.List;
import java.util.Optional;

public interface SpecialtyService {
    Specialty save(Specialty specialty);

    Specialty update(Specialty specialty);

    void deleteBy(Long id);

    List<Specialty> getAll();

    Optional<Specialty> getById(Long id);

    void saveIfAbsent(Specialty specialty);
}
