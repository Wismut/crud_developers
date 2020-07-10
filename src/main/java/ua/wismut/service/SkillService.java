package ua.wismut.service;

import ua.wismut.model.Skill;

import java.util.List;
import java.util.Optional;


public interface SkillService {
    Skill save(Skill skill);

    Skill update(Skill skill);

    void deleteById(Long id);

    List<Skill> findAll();

    Optional<Skill> findById(Long id);

    Optional<Skill> findByName(String name);
}
