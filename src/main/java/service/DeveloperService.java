package service;

import model.Developer;
import repository.DeveloperRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeveloperService {
    private DeveloperRepository developerRepository;

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
        return developerRepository.getAll().stream()
                .filter(d -> d.getSpecialty().getName().equalsIgnoreCase(specialityName))
                .collect(Collectors.toList());
    }

    public List<Developer> getAll() {
        return developerRepository.getAll();
    }
}
