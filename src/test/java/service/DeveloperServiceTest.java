package service;

import model.Developer;
import model.Specialty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.DeveloperRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeveloperServiceTest {
    @Mock
    DeveloperRepository developerRepository;

    @InjectMocks
    DeveloperService serviceUnderTest;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deleteById() {
        Developer developer = buildDeveloper();
        serviceUnderTest.deleteById(developer.getId());
        verify(developerRepository, times(1)).deleteBy(developer.getId());
    }

    @Test
    void update() {
        Developer developer = buildDeveloper();
        serviceUnderTest.update(developer);
        verify(developerRepository, times(1)).update(developer);
    }

    @Test
    void save() {
        Developer developer = buildDeveloper();
        when(developerRepository.save(any(Developer.class))).thenReturn(developer);
        Developer savedDeveloper = serviceUnderTest.save(developer);
        assertEquals(developer, savedDeveloper);
    }

    @Test
    void getById() {
        Optional<Developer> developer = Optional.of(buildDeveloper());
        when(developerRepository.getById(developer.get().getId())).thenReturn(developer);
        Optional<Developer> developerById = serviceUnderTest.getById(developer.get().getId());
        assertEquals(developer, developerById);
    }

    @Test
    void getAllBySpeciality() {
        List<Developer> developers = Collections.singletonList(buildDeveloper());
        when(developerRepository.getAllBySpecialty(developers.get(0).getSpecialty().getName())).thenReturn(developers);
        List<Developer> developersBySpecialty = serviceUnderTest.getAllBySpeciality(developers.get(0).getSpecialty().getName());
        assertEquals(developers, developersBySpecialty);
    }

    @Test
    void getAll() {
        List<Developer> developers = Collections.singletonList(buildDeveloper());
        when(developerRepository.getAll()).thenReturn(developers);
        List<Developer> allDevelopers = serviceUnderTest.getAll();
        assertEquals(developers, allDevelopers);
    }

    private Developer buildDeveloper() {
        Developer developer = new Developer("first_name", "last_name", new Specialty("specialty_name"), Collections.emptyList());
        developer.setId(38L);
        return developer;
    }
}
