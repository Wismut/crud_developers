package ua.wismut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ua.wismut.model.Developer;
import ua.wismut.repository.DeveloperRepository;
import ua.wismut.service.DeveloperService;
import ua.wismut.validator.DeveloperValidator;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperServiceImpl implements DeveloperService {
    private final DeveloperRepository developerRepository;
    private final DeveloperValidator developerValidator;

    @Autowired
    public DeveloperServiceImpl(DeveloperRepository developerRepository, DeveloperValidator developerValidator) {
        this.developerRepository = developerRepository;
        this.developerValidator = developerValidator;
    }

    public void deleteById(Long id) {
        developerRepository.deleteById(id);
    }

    public Developer update(Developer developer) {
        return developerRepository.save(developer);
    }

    public Developer save(Developer developer, BindingResult bindingResult) {
        developerValidator.validate(developer, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Bad request");
        }
        return developerRepository.save(developer);
    }

    public Optional<Developer> findById(Long id) {
        return developerRepository.findById(id);
    }

    public List<Developer> findAllBySpeciality(String specialityName) {
        return developerRepository.findAllBySpecialtyName(specialityName);
    }

    public List<Developer> findAll() {
        return developerRepository.findAll();
    }
}
