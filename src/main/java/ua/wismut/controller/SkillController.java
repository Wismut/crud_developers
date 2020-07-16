package ua.wismut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.wismut.exception.SkillNotFoundException;
import ua.wismut.model.Skill;
import ua.wismut.service.SkillService;
import ua.wismut.validator.SkillValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/skills/")
public class SkillController {
    private final SkillService skillService;
    private final SkillValidator skillValidator;

    @Autowired
    public SkillController(SkillService skillService, SkillValidator skillValidator) {
        this.skillService = skillService;
        this.skillValidator = skillValidator;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        skillService.deleteById(id);
    }

    @PostMapping
    public Skill save(@RequestBody Skill skill, BindingResult bindingResult) {
        skillValidator.validate(skill, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Skill name can't be null or empty");
        }
        return skillService.save(skill);
    }

    @PutMapping
    public Skill update(Skill skill) {
        return skillService.update(skill);
    }

    @GetMapping("{id}")
    public Skill findById(@PathVariable Long id) {
        return skillService.findById(id).orElseThrow(() -> new SkillNotFoundException(id));
    }

    @GetMapping
    public List<Skill> findAll() {
        return skillService.findAll();
    }

    public Optional<Skill> findByName(String name) {
        return skillService.findByName(name);
    }
}
