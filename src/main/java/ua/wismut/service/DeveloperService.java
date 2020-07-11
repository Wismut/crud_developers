package ua.wismut.service;

import ua.wismut.model.Developer;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    void deleteById(Long id);

    Developer update(Developer developer);

    Developer save(Developer developer);

    Optional<Developer> findById(Long id);

    List<Developer> findAllBySpeciality(String specialityName);

    List<Developer> findAll();
}
