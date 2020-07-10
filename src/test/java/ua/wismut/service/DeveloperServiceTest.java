package ua.wismut.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.wismut.model.Account;
import ua.wismut.model.Developer;
import ua.wismut.model.Specialty;
import ua.wismut.repository.DeveloperRepository;
import ua.wismut.service.impl.DeveloperServiceImpl;

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
    DeveloperServiceImpl serviceUnderTest;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deleteById() {
        Developer developer = buildDeveloper();
        serviceUnderTest.deleteById(developer.getId());
        verify(developerRepository, times(1)).deleteById(developer.getId());
    }

    @Test
    void update() {
        Developer developer = buildDeveloper();
        serviceUnderTest.update(developer);
        verify(developerRepository, times(1)).save(developer);
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
        when(developerRepository.findById(developer.get().getId())).thenReturn(developer);
        Optional<Developer> developerById = serviceUnderTest.getById(developer.get().getId());
        assertEquals(developer, developerById);
    }

    @Test
    void getAllBySpeciality() {
        List<Developer> developers = Collections.singletonList(buildDeveloper());
        when(developerRepository.findAllBySpecialtyName(developers.get(0).getSpecialty().getName())).thenReturn(developers);
        List<Developer> developersBySpecialty = serviceUnderTest.getAllBySpeciality(developers.get(0).getSpecialty().getName());
        assertEquals(developers, developersBySpecialty);
    }

    @Test
    void getAll() {
        List<Developer> developers = Collections.singletonList(buildDeveloper());
        when(developerRepository.findAll()).thenReturn(developers);
        List<Developer> allDevelopers = serviceUnderTest.findAll();
        assertEquals(developers, allDevelopers);
    }

    private Developer buildDeveloper() {
        return new Developer(38L,
                "first_name",
                "last_name",
                new Specialty("specialty_name"),
                Collections.emptySet(),
                new Account());
    }
}