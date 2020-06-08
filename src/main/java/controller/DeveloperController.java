package controller;

import model.Developer;
import model.DeveloperSkill;
import service.DeveloperService;
import service.DeveloperSkillService;
import service.SkillService;
import service.SpecialtyService;

import java.util.List;
import java.util.Optional;

public class DeveloperController {
    private final DeveloperService developerService;
    private final SkillService skillService;
    private final SpecialtyService specialtyService;
    private final DeveloperSkillService developerSkillService;

    public DeveloperController(DeveloperService developerService, SkillService skillService, SpecialtyService specialtyService, DeveloperSkillService developerSkillService) {
        this.developerService = developerService;
        this.skillService = skillService;
        this.specialtyService = specialtyService;
        this.developerSkillService = developerSkillService;
    }

    public void deleteById(Long id) {
        developerService.deleteById(id);
    }

    public Developer update(Developer developer) {
        return developerService.update(developer);
    }

    public Developer save(Developer developer) {
        List<Long> skillIds = skillService.saveIfAbsent(developer.getSkills());
        specialtyService.saveIfAbsent(developer.getSpecialty());
        developerService.save(developer);
        skillIds.forEach(s -> developerSkillService.save(new DeveloperSkill(developer.getId(), s)));
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
