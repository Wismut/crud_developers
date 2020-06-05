package controller;

import model.Specialty;
import service.SpecialtyService;

import java.util.List;
import java.util.Optional;

public class SpecialtyController {
    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    public Specialty save(Specialty specialty) {
        return specialtyService.save(specialty);
    }

    public Specialty update(Specialty specialty) {
        return specialtyService.update(specialty);
    }

    public void deleteById(Long id) {
        specialtyService.deleteBy(id);
    }

    public List<Specialty> getAll() {
        return specialtyService.getAll();
    }

    public Optional<Specialty> getById(Long id) {
        return specialtyService.getById(id);
    }
}
