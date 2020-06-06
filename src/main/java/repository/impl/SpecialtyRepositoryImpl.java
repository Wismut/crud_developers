package repository.impl;

import model.Specialty;
import repository.SpecialtyRepository;

import java.util.List;
import java.util.Optional;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public Optional<Specialty> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Specialty> getAll() {
        return null;
    }

    @Override
    public Specialty save(Specialty specialty) {
        return null;
    }

    @Override
    public void deleteBy(Long id) {

    }

    @Override
    public Specialty update(Specialty specialty) {
        return null;
    }
}
