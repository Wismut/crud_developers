package ua.wismut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.wismut.exception.DeveloperNotFoundException;
import ua.wismut.model.Developer;
import ua.wismut.service.DeveloperService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers/")
public class DeveloperController {
    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @DeleteMapping
    public void deleteById(Long id) {
        developerService.deleteById(id);
    }

    @PutMapping
    public Developer update(@RequestBody Developer developer) {
        return developerService.update(developer);
    }

    @PostMapping
    public Developer save(@RequestBody Developer developer) {
        return developerService.save(developer);
    }

    @GetMapping("{id}")
    public Developer findById(@PathVariable Long id) {
        return developerService.findById(id).orElseThrow(() -> new DeveloperNotFoundException(id));
    }

    public List<Developer> getAllBySpeciality(String specialityName) {
        return developerService.findAllBySpeciality(specialityName);
    }

    @GetMapping
    public List<Developer> findAll() {
        return developerService.findAll();
    }
}
