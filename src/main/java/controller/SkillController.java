package controller;

import model.Skill;
import service.SkillService;

import java.util.List;
import java.util.Optional;

public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    public void deleteById(Long id) {
        skillService.deleteBy(id);
    }

    public Skill save(Skill skill) {
        return skillService.save(skill);
    }

    public void update(Skill skill) {

    }

    public Optional<Skill> getById(Long id) {
        return Optional.empty();
    }

    public List<Skill> getAll() {
        return null;
    }

    public Optional<Skill> getByName(String name) {
        return null;
    }
}
