package ua.wismut.service;

import org.springframework.validation.BindingResult;
import ua.wismut.model.Specialty;

import java.util.List;
import java.util.Optional;

public interface SpecialtyService {
    Specialty save(Specialty specialty, BindingResult bindingResult);

    Specialty update(Specialty specialty);

    void deleteBy(Long id);

    List<Specialty> findAll();

    Optional<Specialty> findById(Long id);
}
