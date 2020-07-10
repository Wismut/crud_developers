package ua.wismut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.wismut.exception.SkillNotFoundException;
import ua.wismut.model.Skill;
import ua.wismut.service.SkillService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/skills/")
public class SkillController {
    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    public void deleteById(Long id) {
        skillService.deleteBy(id);
    }

    public Skill save(Skill skill) {
        return skillService.save(skill);
    }

    public Skill update(Skill skill) {
        return skillService.update(skill);
    }

    @GetMapping("{id}")
    public Skill getById(@PathVariable Long id) {
        return skillService.getById(id).orElseThrow(() -> new SkillNotFoundException(id));
    }

    @GetMapping
    public List<Skill> getAll() {
        return skillService.getAll();
    }

    public Optional<Skill> getByName(String name) {
        return skillService.getByName(name);
    }
}
