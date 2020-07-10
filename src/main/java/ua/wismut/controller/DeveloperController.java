package ua.wismut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.wismut.model.Developer;
import ua.wismut.service.DeveloperService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/developers/")
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
        developerService.save(developer);
        return developer;
    }

    public Optional<Developer> findById(Long id) {
        return developerService.getById(id);
    }

    public List<Developer> getAllBySpeciality(String specialityName) {
        return developerService.getAllBySpeciality(specialityName);
    }

    public List<Developer> findAll() {
        return developerService.findAll();
    }
}
