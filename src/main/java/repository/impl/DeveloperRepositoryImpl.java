package repository.impl;

import model.Developer;
import repository.DeveloperRepository;

import java.util.List;
import java.util.Optional;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    @Override
    public Optional<Developer> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Developer> getAll() {
        return null;
    }

    @Override
    public Developer save(Developer entity) {
        return null;
    }

    @Override
    public void deleteBy(Long aLong) {

    }

    @Override
    public Developer update(Developer entity) {
        return null;
    }
}
