package ua.wismut.service;

import ua.wismut.model.Specialty;

import java.util.List;
import java.util.Optional;

public interface SpecialtyService {
    Specialty save(Specialty specialty);

    Specialty update(Specialty specialty);

    void deleteBy(Long id);

    List<Specialty> findAll();

    Optional<Specialty> findById(Long id);

    void saveIfAbsent(Specialty specialty);
}
