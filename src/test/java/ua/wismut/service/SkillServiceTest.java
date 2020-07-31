package ua.wismut.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import ua.wismut.model.Skill;
import ua.wismut.repository.SkillRepository;
import ua.wismut.service.impl.SkillServiceImpl;
import ua.wismut.validator.SkillValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SkillServiceTest {
    @Mock
    SkillRepository skillRepository;

    @Mock
    SkillValidator skillValidator;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    SkillServiceImpl serviceUnderTest;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void before() {
        Mockito.reset(skillRepository);
    }

    @Test
    public void save() {
        Skill skill = buildSkill();
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);
        Skill savedSkill = serviceUnderTest.save(skill, bindingResult);
        assertEquals(skill, savedSkill);
        verify(skillRepository, times(1)).save(any(Skill.class));
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    public void whenSaveWithBadCredentialsThenThrowIllegalArgumentException() {
        Skill skill = buildSkill();
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);
        when(bindingResult.hasErrors()).thenReturn(true);
        assertThrows(IllegalArgumentException.class,
                () -> serviceUnderTest.save(skill, bindingResult),
                "Skill name can't be null or empty");
        verify(skillRepository, times(0)).save(any(Skill.class));
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    void findAll() {
        List<Skill> skills = Collections.singletonList(buildSkill());
        when(skillRepository.findAll()).thenReturn(skills);
        List<Skill> allSkills = serviceUnderTest.findAll();
        assertEquals(skills, allSkills);
        verify(skillRepository, times(1)).findAll();
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    public void update() {
        Skill skill = buildSkill();
        when(skillRepository.save(any())).thenReturn(skill);
        when(bindingResult.hasErrors()).thenReturn(false);
        Skill updatedSkill = serviceUnderTest.update(skill, bindingResult);
        assertEquals(skill, updatedSkill);
        verify(skillRepository, times(1)).save(skill);
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    void deleteBy() {
        Skill skill = buildSkill();
        serviceUnderTest.deleteById(skill.getId());
        verify(skillRepository, times(1)).deleteById(skill.getId());
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    void delete() {
        Skill skill = buildSkill();
        serviceUnderTest.delete(skill);
        verify(skillRepository, times(1)).delete(skill);
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    void findById() {
        Optional<Skill> skill = Optional.of(buildSkill());
        when(skillRepository.findById(skill.get().getId())).thenReturn(skill);
        Optional<Skill> foundSkill = serviceUnderTest.findById(skill.get().getId());
        assertEquals(skill, foundSkill);
        verify(skillRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    void findByName() {
        Optional<Skill> skill = Optional.of(buildSkill());
        when(skillRepository.findByName(skill.get().getName())).thenReturn(skill);
        Optional<Skill> foundSkill = serviceUnderTest.findByName(skill.get().getName());
        assertEquals(skill, foundSkill);
        verify(skillRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(skillRepository);
    }

    @Test
    void saveIfAbsent() {
        Skill skill = buildSkill();
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);
        Skill savedSkill = serviceUnderTest.save(skill, bindingResult);
        assertEquals(skill, savedSkill);
        verify(skillRepository, times(1)).save(any(Skill.class));
        verifyNoMoreInteractions(skillRepository);
    }

    private Skill buildSkill() {
        return new Skill(23L, "some_name");
    }
}
