package service;

import model.Developer;
import repository.DeveloperRepository;

import java.util.List;
import java.util.Optional;

public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public void deleteById(Long id) {
        developerRepository.deleteBy(id);
    }

    public Developer update(Developer developer) {
        return developerRepository.update(developer);
    }

    public Developer save(Developer developer) {
        return developerRepository.save(developer);
    }

    public Optional<Developer> getById(Long id) {
        return developerRepository.getById(id);
    }

    public List<Developer> getAllBySpeciality(String specialityName) {
        return developerRepository.getAllBySpecialty(specialityName);
    }

    public List<Developer> getAll() {
        return developerRepository.getAll();
    }
}
