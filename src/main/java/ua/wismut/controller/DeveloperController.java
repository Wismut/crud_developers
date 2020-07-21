package ua.wismut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.wismut.exception.DeveloperNotFoundException;
import ua.wismut.model.Developer;
import ua.wismut.service.DeveloperService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {
    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        developerService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Developer update(@RequestBody Developer developer, @PathVariable Long id) {
        return developerService.update(developer, developer.getId());
    }

    @PostMapping
    public Developer save(@RequestBody Developer developer, BindingResult bindingResult) {
        return developerService.save(developer, bindingResult);
    }

    @GetMapping("{id}")
    public Developer findById(@PathVariable Long id) {
        return developerService.findById(id).orElseThrow(() -> new DeveloperNotFoundException(id));
    }

    public List<Developer> findAllBySpeciality(String specialityName) {
        return developerService.findAllBySpeciality(specialityName);
    }

    @GetMapping
    public List<Developer> findAll() {
        return developerService.findAll();
    }
}
