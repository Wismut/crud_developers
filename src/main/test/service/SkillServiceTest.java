package service;

import model.Skill;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.SkillRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SkillServiceTest {
    @Mock
    SkillRepository skillRepository;

    @InjectMocks
    SkillService skillService;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        Skill skill = new Skill("xdfvsf");
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);
        Skill savedSkill = skillService.save(skill);
        assertEquals(skill, savedSkill);
    }

    @Test
    void update() {
    }

    @Test
    void deleteBy() {
    }

    @Test
    void getAll() {
        List<Skill> skills = Collections.singletonList(new Skill("dsvs"));
        when(skillRepository.getAll()).thenReturn(skills);
        List<Skill> allSkills = skillService.getAll();
        assertEquals(skills, allSkills);
    }

    @Test
    void getById() {
        Optional<Skill> skill = Optional.of(new Skill(1L, "xdfvsf"));
        when(skillRepository.getById(1L)).thenReturn(skill);
        Optional<Skill> foundSkill = skillService.getById(1L);
        assertEquals(skill, foundSkill);
    }

    @Test
    void getByName() {
        Optional<Skill> skill = Optional.of(new Skill(1L, "xdfvsf"));
        when(skillRepository.getByName("xdfvsf")).thenReturn(skill);
        Optional<Skill> foundSkill = skillService.getByName("xdfvsf");
        assertEquals(skill, foundSkill);
    }

    @Test
    void saveIfAbsent() {
    }
}