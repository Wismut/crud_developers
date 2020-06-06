package repository.impl;

import model.Skill;
import repository.SkillRepository;

import java.util.List;
import java.util.Optional;

public class SkillRepositoryImpl implements SkillRepository {
    @Override
    public Optional<Skill> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Skill> getAll() {
        return null;
    }

    @Override
    public Skill save(Skill entity) {
        return null;
    }

    @Override
    public void deleteBy(Long aLong) {

    }

    @Override
    public Skill update(Skill entity) {
        return null;
    }
}
