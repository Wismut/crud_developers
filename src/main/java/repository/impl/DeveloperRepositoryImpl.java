package repository.impl;

import model.Developer;
import repository.DeveloperRepository;

import java.util.List;
import java.util.Optional;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    @Override
    public Optional<Developer> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Developer> getAll() {
        return null;
    }

    @Override
    public Developer save(Developer developer) {
        return null;
    }

    @Override
    public void deleteBy(Long id) {

    }

    @Override
    public Developer update(Developer developer) {
        return null;
    }
}
