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
import ua.wismut.model.Specialty;
import ua.wismut.repository.SpecialtyRepository;
import ua.wismut.service.impl.SpecialtyServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpecialtyServiceTest {
    @Mock
    private SpecialtyRepository specialtyRepository;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private SpecialtyServiceImpl serviceUnderTest;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void before() {
        Mockito.reset(specialtyRepository);
    }

    @Test
    void save() {
        Specialty specialty = buildSpecialty();
        when(specialtyRepository.save(any())).thenReturn(specialty);
        Specialty savedSpecialty = serviceUnderTest.save(specialty, bindingResult);
        assertEquals(specialty, savedSpecialty);
        verify(specialtyRepository, times(1)).save(specialty);
        verifyNoMoreInteractions(specialtyRepository);
    }

    @Test
    void update() {
        Specialty specialty = buildSpecialty();
        serviceUnderTest.update(specialty, specialty.getId());
        verify(specialtyRepository, times(1)).save(specialty);
        verifyNoMoreInteractions(specialtyRepository);
    }

    @Test
    void deleteBy() {
        Specialty specialty = buildSpecialty();
        serviceUnderTest.deleteById(specialty.getId());
        verify(specialtyRepository, times(1)).deleteById(specialty.getId());
        verifyNoMoreInteractions(specialtyRepository);
    }

    @Test
    void findAll() {
        List<Specialty> specialties = Collections.singletonList(buildSpecialty());
        when(specialtyRepository.findAll()).thenReturn(specialties);
        List<Specialty> allSpecialties = serviceUnderTest.findAll();
        assertEquals(specialties, allSpecialties);
        verify(specialtyRepository, times(1)).findAll();
        verifyNoMoreInteractions(specialtyRepository);
    }

    @Test
    void findById() {
        Optional<Specialty> specialty = Optional.of(buildSpecialty());
        when(specialtyRepository.findById(specialty.get().getId())).thenReturn(specialty);
        Optional<Specialty> specialtyById = serviceUnderTest.findById(specialty.get().getId());
        assertEquals(specialty, specialtyById);
        verify(specialtyRepository, times(1)).findById(specialty.get().getId());
        verifyNoMoreInteractions(specialtyRepository);
    }

    @Test
    void saveIfAbsent() {
        Specialty specialty = buildSpecialty();
        when(specialtyRepository.save(any())).thenReturn(specialty);
        Specialty savedSpecialty = serviceUnderTest.save(specialty, bindingResult);
        assertEquals(specialty, savedSpecialty);
        verify(specialtyRepository, times(1)).save(specialty);
        verifyNoMoreInteractions(specialtyRepository);
    }

    private Specialty buildSpecialty() {
        return new Specialty(4L, "random_name", "description");
    }
}
