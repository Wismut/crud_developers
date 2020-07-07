package service.impl;

import model.Specialty;
import repository.SpecialtyRepository;

import java.util.List;
import java.util.Optional;

public class SpecialtyServiceImpl {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Specialty save(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public Specialty update(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public void deleteBy(Long id) {
        specialtyRepository.deleteById(id);
    }

    public List<Specialty> getAll() {
        return specialtyRepository.findAll();
    }

    public Optional<Specialty> getById(Long id) {
        return specialtyRepository.findById(id);
    }

    public void saveIfAbsent(Specialty specialty) {
        specialtyRepository.save(specialty);
    }
}
