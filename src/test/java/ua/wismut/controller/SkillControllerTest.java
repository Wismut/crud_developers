package ua.wismut.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.wismut.model.Skill;
import ua.wismut.service.SkillService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillControllerTest {
    @InjectMocks
    private SkillController controllerUnderTest;

    @Mock
    private SkillService skillService;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.initMocks(SkillControllerTest.class);
    }

    @Test
    public void findAllSkillsFoundShouldReturnFoundSkillEntries() throws Exception {
        Skill firstSkill = new Skill(1L, "some name");
        Skill secondSkill = new Skill(2L, "nameSkill");

        when(skillService.findAll()).thenReturn(Arrays.asList(firstSkill, secondSkill));

        List<Skill> skills = controllerUnderTest.findAll(null);
        assertEquals(skills.size(), 2);
        assertEquals(skills.get(0).getId().longValue(), 1L);
        assertEquals(skills.get(1).getId().longValue(), 2L);

        verify(skillService, times(1)).findAll();
        verifyNoMoreInteractions(skillService);
    }

    @Test
    public void findByIdShouldReturnFoundSkillEntry() throws Exception {
        Skill skill = new Skill(1L, "some name");

        when(skillService.findById(skill.getId())).thenReturn(Optional.of(skill));

        Skill foundedSkill = controllerUnderTest.findById(skill.getId());
        assertEquals(foundedSkill.getId(), skill.getId());
        assertEquals(foundedSkill.getName(), skill.getName());

        verify(skillService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(skillService);
    }

    @Test
    public void saveShouldReturnFoundSkillEntry() throws Exception {
        Skill skill = new Skill("some name");

        when(skillService.save(skill)).thenReturn(skill);

        Skill savedSkill = controllerUnderTest.save(skill, null);
        assertEquals(savedSkill.getId(), skill.getId());
        assertEquals(savedSkill.getName(), skill.getName());

        verify(skillService, times(1)).save(any());
        verifyNoMoreInteractions(skillService);
    }
}
