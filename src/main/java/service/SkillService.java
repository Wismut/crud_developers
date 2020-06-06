package service;

import model.Skill;
import repository.SkillRepository;

import java.util.List;
import java.util.Optional;

public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill update(Skill skill) {
        return skillRepository.update(skill);
    }

    public void deleteBy(Long id) {
        skillRepository.deleteBy(id);
    }

    public List<Skill> getAll() {
        return skillRepository.getAll();
    }

    public Optional<Skill> getById(Long id) {
        return skillRepository.getById(id);
    }
}
