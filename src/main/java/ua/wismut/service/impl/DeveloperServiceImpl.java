package ua.wismut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.wismut.model.Developer;
import ua.wismut.repository.DeveloperRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperServiceImpl {
    private final DeveloperRepository developerRepository;

    @Autowired
    public DeveloperServiceImpl(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public void deleteById(Long id) {
        developerRepository.deleteById(id);
    }

    public Developer update(Developer developer) {
        return developerRepository.saveAndFlush(developer);
    }

    public Developer save(Developer developer) {
        return developerRepository.save(developer);
    }

    public Optional<Developer> getById(Long id) {
        return developerRepository.findById(id);
    }

    public List<Developer> getAllBySpeciality(String specialityName) {
        return developerRepository.findAllBySpecialtyName(specialityName);
    }

    public List<Developer> getAll() {
        return developerRepository.findAll();
    }
}
