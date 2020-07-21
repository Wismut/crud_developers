package ua.wismut.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import ua.wismut.model.Developer;
import ua.wismut.service.DeveloperService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeveloperControllerTest {
    @InjectMocks
    private DeveloperController controllerUnderTest;

    @Mock
    private DeveloperService developerService;

    @Mock
    private BindingResult bindingResult;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.initMocks(DeveloperControllerTest.class);
    }

    @Test
    public void findAllDevelopersFoundShouldReturnFoundDeveloperEntries() throws Exception {
        Developer firstDeveloper = buildDeveloper(1L, "Mike", "Halts");
        Developer secondDeveloper = buildDeveloper(2L, "Deny", "Richy");

        when(developerService.findAll()).thenReturn(Arrays.asList(firstDeveloper, secondDeveloper));

        List<Developer> developers = controllerUnderTest.findAll();
        assertEquals(2, developers.size());
        assertEquals(1L, developers.get(0).getId().longValue());
        assertEquals(2L, developers.get(1).getId().longValue());
        assertEquals("Mike", developers.get(0).getFirstName());
        assertEquals("Deny", developers.get(1).getFirstName());
        assertEquals("Halts", developers.get(0).getLastName());
        assertEquals("Richy", developers.get(1).getLastName());

        verify(developerService, times(1)).findAll();
        verifyNoMoreInteractions(developerService);
    }

    @Test
    public void findByIdShouldReturnFoundDeveloperEntry() throws Exception {
        Developer developer = buildDeveloper();

        when(developerService.findById(developer.getId())).thenReturn(Optional.of(developer));

        Developer foundedDeveloper = controllerUnderTest.findById(developer.getId());
        assertEquals(developer.getId(), foundedDeveloper.getId());
        assertEquals(developer.getFirstName(), foundedDeveloper.getFirstName());
        assertEquals(developer.getLastName(), foundedDeveloper.getLastName());

        verify(developerService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(developerService);
    }

    @Test
    public void saveShouldReturnSavedDeveloperEntry() throws Exception {
        Developer developer = buildDeveloper();

        when(developerService.save(developer, bindingResult)).thenReturn(developer);

        Developer savedDeveloper = controllerUnderTest.save(developer, bindingResult);
        assertEquals(developer, savedDeveloper);

        verify(developerService, times(1)).save(any(), any());
        verifyNoMoreInteractions(developerService);
    }

    @Test
    public void updateShouldReturnUpdatedDeveloperEntry() throws Exception {
        Developer developer = buildDeveloper();

        when(developerService.update(developer, developer.getId())).thenReturn(developer);

        Developer updatedDeveloper = controllerUnderTest.update(developer, developer.getId());
        assertEquals(developer, updatedDeveloper);

        verify(developerService, times(1)).update(any(), anyLong());
        verifyNoMoreInteractions(developerService);
    }

    @Test
    public void deleteById() {
        Long developerId = 5L;

        controllerUnderTest.deleteById(developerId);

        verify(developerService, times(1)).deleteById(5L);
        verifyNoMoreInteractions(developerService);
    }

    private Developer buildDeveloper() {
        Developer developer = new Developer();
        developer.setId(7L);
        developer.setFirstName("A");
        developer.setLastName("B");
        return developer;
    }

    private Developer buildDeveloper(Long id, String firstName, String lastName) {
        Developer developer = new Developer();
        developer.setId(id);
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        return developer;
    }
}
