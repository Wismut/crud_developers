package controller;

import model.Developer;
import service.DeveloperService;
import service.SkillService;
import service.SpecialtyService;

import java.util.List;
import java.util.Optional;

public class DeveloperController {
    private final DeveloperService developerService;
    private final SkillService skillService;
    private final SpecialtyService specialtyService;

    public DeveloperController(DeveloperService developerService, SkillService skillService, SpecialtyService specialtyService) {
        this.developerService = developerService;
        this.skillService = skillService;
        this.specialtyService = specialtyService;
    }

    public void deleteById(Long id) {
        developerService.deleteById(id);
    }

    public Developer update(Developer developer) {
        return developerService.update(developer);
    }

    public Developer save(Developer developer) {
        developerService.save(developer);
        return developer;
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
