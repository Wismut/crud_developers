package ua.wismut.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.wismut.config.SpringWebConfig;
import ua.wismut.model.Skill;
import ua.wismut.service.SkillService;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;

//@ExtendWith(SpringExtension.class)
//@Tag("Controller")
//@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = {SpringWebConfig.class})
//@TestExecutionListeners({
//        MyCustomTestExecutionListener.class,
//        ServletTestExecutionListener.class,
//        DirtiesContextBeforeModesTestExecutionListener.class,
//        DependencyInjectionTestExecutionListener.class,
//        DirtiesContextTestExecutionListener.class,
//        TransactionalTestExecutionListener.class,
//        SqlScriptsTestExecutionListener.class
//})
//@WebAppConfiguration
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = SpringWebConfig.class)
public class SkillControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private SkillService skillService;

    @Test
    public void findAll_TodosFound_ShouldReturnFoundTodoEntries() throws Exception {
        Skill firstSkill = new Skill(1L, "some name");
        Skill secondSkill = new Skill(2L, "nameSkill");

        when(skillService.findAll()).thenReturn(Arrays.asList(firstSkill, secondSkill));

        mockMvc.perform(get("/api/v1/skills/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("some name")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("nameSkill")));

        verify(skillService, times(1)).findAll();
        verifyNoMoreInteractions(skillService);
    }
}