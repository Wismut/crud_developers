package ua.wismut.service;

import ua.wismut.model.Developer;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    void deleteById(Long id);

    Developer update(Developer developer);

    Developer save(Developer developer);

    Optional<Developer> getById(Long id);

    List<Developer> getAllBySpeciality(String specialityName);

    List<Developer> findAll();
}
