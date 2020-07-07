package service;

import model.Skill;

import java.util.List;
import java.util.Optional;


public interface SkillService {
    Skill save(Skill skill);

    Skill update(Skill skill);

    void deleteBy(Long id);

    List<Skill> getAll();

    Optional<Skill> getById(Long id);

    Optional<Skill> getByName(String name);
}
