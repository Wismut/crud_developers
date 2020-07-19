package ua.wismut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ua.wismut.model.Skill;
import ua.wismut.repository.SkillRepository;
import ua.wismut.service.SkillService;
import ua.wismut.validator.SkillValidator;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillValidator skillValidator;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository, SkillValidator skillValidator) {
        this.skillRepository = skillRepository;
        this.skillValidator = skillValidator;
    }

    public Skill save(Skill skill, BindingResult bindingResult) {
        skillValidator.validate(skill, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Skill name can't be null or empty");
        }
        return skillRepository.save(skill);
    }

    public Skill update(Skill skill, Long id, BindingResult bindingResult) {
        skillValidator.validate(skill, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Skill name can't be null or empty");
        }
        skill.setId(id);
        return skillRepository.save(skill);
    }

    public void deleteById(Long id) {
        skillRepository.deleteById(id);
    }

    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

    public Optional<Skill> findByName(String name) {
        return skillRepository.findByName(name);
    }

    @Override
    public void delete(Skill skill) {
        skillRepository.delete(skill);
    }
}
