package ua.wismut.service;

import org.springframework.validation.BindingResult;
import ua.wismut.model.Developer;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    void deleteById(Long id);

    Developer update(Developer developer, Long id);

    Developer save(Developer developer, BindingResult bindingResult);

    Optional<Developer> findById(Long id);

    List<Developer> findAllBySpeciality(String specialityName);

    List<Developer> findAll();
}
