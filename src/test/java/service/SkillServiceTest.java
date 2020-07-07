package service;

import model.Skill;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.SkillRepository;
import service.impl.SkillServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SkillServiceTest {
    @Mock
    SkillRepository skillRepository;

    @InjectMocks
    SkillServiceImpl serviceUnderTest;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        Skill skill = buildSkillWithName();
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);
        Skill savedSkill = serviceUnderTest.save(skill);
        assertEquals(skill, savedSkill);
    }

    @Test
    void update() {
        Skill skill = buildSkillWithName();
        serviceUnderTest.update(skill);
        verify(skillRepository, times(1)).save(skill);
    }

    @Test
    void deleteBy() {
        Skill skill = buildSkillWithName();
        serviceUnderTest.deleteBy(skill.getId());
        verify(skillRepository, times(1)).deleteById(skill.getId());
    }

    @Test
    void getAll() {
        List<Skill> skills = Collections.singletonList(buildSkillWithName());
        when(skillRepository.findAll()).thenReturn(skills);
        List<Skill> allSkills = serviceUnderTest.getAll();
        assertEquals(skills, allSkills);
    }

    @Test
    void getById() {
        Optional<Skill> skill = Optional.of(buildSkillWithNameAndId());
        when(skillRepository.findById(skill.get().getId())).thenReturn(skill);
        Optional<Skill> foundSkill = serviceUnderTest.getById(skill.get().getId());
        assertEquals(skill, foundSkill);
    }

    @Test
    void getByName() {
        Optional<Skill> skill = Optional.of(buildSkillWithNameAndId());
        when(skillRepository.getByName(skill.get().getName())).thenReturn(skill);
        Optional<Skill> foundSkill = serviceUnderTest.getByName(skill.get().getName());
        assertEquals(skill, foundSkill);
    }

    @Test
    void saveIfAbsent() {
        Skill skill = buildSkillWithName();
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);
        Skill savedSkill = serviceUnderTest.save(skill);
        assertEquals(skill, savedSkill);
    }

    private Skill buildSkillWithName() {
        return new Skill("name_random");
    }

    private Skill buildSkillWithNameAndId() {
        return new Skill(23L, "some_name");
    }
}
