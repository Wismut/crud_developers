package controller;

import model.Developer;
import service.DeveloperService;

import java.util.List;
import java.util.Optional;

public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    public void deleteById(Long id) {
        developerService.deleteById(id);
    }

    public Developer update(Developer developer) {
        return developerService.update(developer);
    }

    public Developer save(Developer developer) {
        return developerService.save(developer);
    }

    public Optional<Developer> getById(Long id) {
        return developerService.getById(id);
    }

    public List<Developer> getAllBySpeciality(String specialityName) {
        return developerService.getAllBySpeciality(specialityName);
    }

    public List<Developer> getAll() {
        return developerService.getAll();
    }
}
