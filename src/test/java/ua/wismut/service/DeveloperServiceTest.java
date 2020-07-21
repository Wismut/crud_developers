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

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    DeveloperServiceImpl serviceUnderTest;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void before() {
        Mockito.reset(developerRepository);
    }

    @Test
    void deleteById() {
        Developer developer = buildDeveloper();
        serviceUnderTest.deleteById(developer.getId());
        verify(developerRepository, times(1)).deleteById(developer.getId());
        verifyNoMoreInteractions(developerRepository);
    }

    @Test
    void update() {
        Developer developer = buildDeveloper();
        serviceUnderTest.update(developer, developer.getId());
        verify(developerRepository, times(1)).save(developer);
        verifyNoMoreInteractions(developerRepository);
    }

    @Test
    void save() {
        Developer developer = buildDeveloper();
        when(developerRepository.save(any(Developer.class))).thenReturn(developer);
        Developer savedDeveloper = serviceUnderTest.save(developer, bindingResult);
        assertEquals(developer, savedDeveloper);
        verify(developerRepository, times(1)).save(developer);
        verifyNoMoreInteractions(developerRepository);
    }

    @Test
    void findById() {
        Optional<Developer> developer = Optional.of(buildDeveloper());
        when(developerRepository.findById(developer.get().getId())).thenReturn(developer);
        Optional<Developer> developerById = serviceUnderTest.findById(developer.get().getId());
        assertEquals(developer, developerById);
        verify(developerRepository, times(1)).findById(developer.get().getId());
        verifyNoMoreInteractions(developerRepository);
    }

    @Test
    void findAllBySpeciality() {
        List<Developer> developers = Collections.singletonList(buildDeveloper());
        when(developerRepository.findAllBySpecialtyName(developers.get(0).getSpecialty().getName())).thenReturn(developers);
        List<Developer> developersBySpecialty = serviceUnderTest.findAllBySpeciality(developers.get(0).getSpecialty().getName());
        assertEquals(developers, developersBySpecialty);
        verify(developerRepository, times(1)).findAllBySpecialtyName(developers.get(0).getSpecialty().getName());
        verifyNoMoreInteractions(developerRepository);
    }

    @Test
    void findAll() {
        List<Developer> developers = Collections.singletonList(buildDeveloper());
        when(developerRepository.findAll()).thenReturn(developers);
        List<Developer> allDevelopers = serviceUnderTest.findAll();
        assertEquals(developers, allDevelopers);
        verify(developerRepository, times(1)).findAll();
        verifyNoMoreInteractions(developerRepository);
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
