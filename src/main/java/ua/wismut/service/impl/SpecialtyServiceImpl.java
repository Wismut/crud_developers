package ua.wismut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ua.wismut.model.Specialty;
import ua.wismut.repository.SpecialtyRepository;
import ua.wismut.service.SpecialtyService;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Specialty save(Specialty specialty, BindingResult bindingResult) {
        return specialtyRepository.save(specialty);
    }

    public Specialty update(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public void deleteBy(Long id) {
        specialtyRepository.deleteById(id);
    }

    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }

    public Optional<Specialty> findById(Long id) {
        return specialtyRepository.findById(id);
    }
}
