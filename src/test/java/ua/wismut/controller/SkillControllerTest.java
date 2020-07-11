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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillControllerTest {
    @InjectMocks
    private SkillController skillController;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.initMocks(SkillControllerTest.class);
    }

    @Mock
    private SkillService skillService;

    @Test
    public void findAllSkillsFoundShouldReturnFoundSkillEntries() throws Exception {
        Skill firstSkill = new Skill(1L, "some name");
        Skill secondSkill = new Skill(2L, "nameSkill");

        when(skillService.findAll()).thenReturn(Arrays.asList(firstSkill, secondSkill));

        List<Skill> skills = skillController.findAll();
        assertEquals(skills.size(), 2);
        assertEquals(skills.get(0).getId().longValue(), 1L);
        assertEquals(skills.get(1).getId().longValue(), 2L);

        verify(skillService, times(1)).findAll();
        verifyNoMoreInteractions(skillService);
    }
}