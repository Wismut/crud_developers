package ua.wismut.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
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

    @Mock
    private BindingResult bindingResult;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.initMocks(SkillControllerTest.class);
    }

    @Test
    public void findAllSkillsFoundShouldReturnFoundSkillEntries() throws Exception {
        Skill firstSkill = buildSkill(1L, "some name");
        Skill secondSkill = buildSkill(2L, "nameSkill");

        when(skillService.findAll()).thenReturn(Arrays.asList(firstSkill, secondSkill));

        List<Skill> skills = controllerUnderTest.findAll();
        assertEquals(skills.size(), 2);
        assertEquals(skills.get(0).getId().longValue(), 1L);
        assertEquals(skills.get(1).getId().longValue(), 2L);

        verify(skillService, times(1)).findAll();
        verifyNoMoreInteractions(skillService);
    }

    @Test
    public void findByIdShouldReturnFoundSkillEntry() throws Exception {
        Skill skill = buildSkill();

        when(skillService.findById(skill.getId())).thenReturn(Optional.of(skill));

        Skill foundedSkill = controllerUnderTest.findById(skill.getId());
        assertEquals(foundedSkill.getId(), skill.getId());
        assertEquals(foundedSkill.getName(), skill.getName());

        verify(skillService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(skillService);
    }

    @Test
    public void saveShouldReturnSavedSkillEntry() throws Exception {
        Skill skill = buildSkill();

        when(skillService.save(skill, bindingResult)).thenReturn(skill);

        Skill savedSkill = controllerUnderTest.save(skill, bindingResult);
        assertEquals(savedSkill.getId(), skill.getId());
        assertEquals(savedSkill.getName(), skill.getName());

        verify(skillService, times(1)).save(any(), any());
        verifyNoMoreInteractions(skillService);
    }

    @Test
    public void updateShouldReturnUpdatedSkillEntry() throws Exception {
        Skill skill = buildSkill();

        when(skillService.update(skill, bindingResult)).thenReturn(skill);

        Skill updatedSkill = controllerUnderTest.update(skill, bindingResult);
        assertEquals(updatedSkill.getId(), skill.getId());
        assertEquals(updatedSkill.getName(), skill.getName());

        verify(skillService, times(1)).update(any(), any());
        verifyNoMoreInteractions(skillService);
    }

    @Test
    public void deleteById() {
        Long skillId = 5L;

        controllerUnderTest.deleteById(skillId);

        verify(skillService, times(1)).deleteById(5L);
        verifyNoMoreInteractions(skillService);
    }

    private Skill buildSkill() {
        return new Skill(23L, "some_name");
    }

    private Skill buildSkill(Long id, String name) {
        return new Skill(id, name);
    }
}
