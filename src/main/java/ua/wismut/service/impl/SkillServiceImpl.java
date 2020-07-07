package ua.wismut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.wismut.model.Skill;
import ua.wismut.repository.SkillRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl {
    private final SkillRepository skillRepository;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill update(Skill skill) {
        return skillRepository.save(skill);
    }

    public void deleteBy(Long id) {
        skillRepository.deleteById(id);
    }

    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    public Optional<Skill> getById(Long id) {
        return skillRepository.findById(id);
    }

    public Optional<Skill> getByName(String name) {
        return skillRepository.getByName(name);
    }
}
