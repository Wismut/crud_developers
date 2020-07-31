package ua.wismut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.wismut.exception.SkillNotFoundException;
import ua.wismut.model.Skill;
import ua.wismut.service.SkillService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {
    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        skillService.deleteById(id);
    }

    @PostMapping
    public Skill save(@RequestBody Skill skill, BindingResult bindingResult) {
        return skillService.save(skill, bindingResult);
    }

    @PutMapping
    public Skill update(@RequestBody Skill skill, BindingResult bindingResult) {
        return skillService.update(skill, bindingResult);
    }

    @GetMapping("/{id}")
    public Skill findById(@PathVariable Long id) {
        return skillService.findById(id).orElseThrow(() -> new SkillNotFoundException(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Skill> findAll() {
        return skillService.findAll();
    }
}
