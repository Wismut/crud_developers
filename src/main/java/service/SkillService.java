package service;

import model.Skill;
import repository.SkillRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Skill> getByName(String name) {
        return skillRepository.getByName(name);
    }

    public List<Long> saveIfAbsent(List<Skill> skills) {
        List<Skill> skillsByNames = skillRepository.getAllByNames(skills.stream()
                .map(Skill::getName)
                .collect(Collectors.toList()));
        List<Skill> skillsToSave = skills.stream()
                .filter(s -> !skillsByNames.contains(s))
                .collect(Collectors.toList());
        return skillRepository.saveBatch(skillsToSave);
    }
}
