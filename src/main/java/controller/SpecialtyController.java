package controller;

import factory.ComponentFactory;
import model.Specialty;
import service.SpecialtyService;

import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = "/api/v1/specialties/*")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    public SpecialtyController() {
        this.specialtyService = ComponentFactory.getBy(SpecialtyService.class);
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
