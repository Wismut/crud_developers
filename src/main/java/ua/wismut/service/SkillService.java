package ua.wismut.service;

import org.springframework.validation.BindingResult;
import ua.wismut.model.Skill;

import java.util.List;
import java.util.Optional;


public interface SkillService {
    Skill save(Skill skill, BindingResult bindingResult);

    Skill update(Skill skill, BindingResult bindingResult);

    void deleteById(Long id);

    List<Skill> findAll();

    Optional<Skill> findById(Long id);

    Optional<Skill> findByName(String name);

    void delete(Skill skill);
}
