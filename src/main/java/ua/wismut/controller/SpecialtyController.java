package ua.wismut.controller;

import org.springframework.web.bind.annotation.*;
import ua.wismut.model.Specialty;
import ua.wismut.service.SpecialtyService;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/specialties/")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping
    public Specialty save(Specialty specialty) {
        return specialtyService.save(specialty);
    }

    @PutMapping
    public Specialty update(Specialty specialty) {
        return specialtyService.update(specialty);
    }

    @DeleteMapping
    public void deleteById(Long id) {
        specialtyService.deleteBy(id);
    }

    @GetMapping
    public List<Specialty> findAll() {
        return specialtyService.findAll();
    }

    @GetMapping("{id}")
    public Optional<Specialty> findById(@PathVariable Long id) {
        return specialtyService.findById(id);
    }
}
