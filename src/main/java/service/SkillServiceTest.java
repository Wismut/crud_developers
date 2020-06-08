package service;

import model.Skill;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.SkillRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class SkillServiceTest {
    @Mock
    SkillRepository skillRepository;

    @InjectMocks
    SkillService skillService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void save() {
        Skill skill = new Skill("xdfvsf");
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);
        Skill savedSkill = skillService.save(skill);
        assertEquals(skill, savedSkill);
    }

    @org.junit.jupiter.api.Test
    void update() {
    }

    @org.junit.jupiter.api.Test
    void deleteBy() {
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        List<Skill> skills = Collections.singletonList(new Skill("dsvs"));
        when(skillRepository.getAll()).thenReturn(skills);
        List<Skill> allSkills = skillService.getAll();
        assertEquals(skills, allSkills);
    }

    @org.junit.jupiter.api.Test
    void getById() {
    }

    @org.junit.jupiter.api.Test
    void getByName() {
    }

    @org.junit.jupiter.api.Test
    void saveIfAbsent() {
    }
}