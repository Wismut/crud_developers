package service;

import model.Specialty;
import repository.SpecialtyRepository;

import java.util.List;
import java.util.Optional;

public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Specialty save(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public Specialty update(Specialty specialty) {
        return specialtyRepository.update(specialty);
    }

    public void deleteBy(Long id) {
        specialtyRepository.deleteBy(id);
    }

    public List<Specialty> getAll() {
        return specialtyRepository.getAll();
    }

    public Optional<Specialty> getById(Long id) {
        return specialtyRepository.getById(id);
    }

    public void saveIfAbsent(Specialty specialty) {
        specialtyRepository.save(specialty);
    }
}
