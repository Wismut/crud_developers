package ua.wismut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.wismut.exception.SpecialtyNotFoundException;
import ua.wismut.model.Specialty;
import ua.wismut.service.SpecialtyService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/specialties")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    @Autowired
    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @PostMapping
    public Specialty save(@RequestBody Specialty specialty) {
        return specialtyService.save(specialty);
    }

    @PutMapping("/{id}")
    public Specialty update(@RequestBody Specialty specialty, @PathVariable Long id) {
        return specialtyService.update(specialty);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        specialtyService.deleteBy(id);
    }

    @GetMapping
    public List<Specialty> findAll() {
        return specialtyService.findAll();
    }

    @GetMapping("{id}")
    public Specialty findById(@PathVariable Long id) {
        return specialtyService.findById(id).orElseThrow(() -> new SpecialtyNotFoundException(id));
    }
}
