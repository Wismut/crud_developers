package ua.wismut.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialtyServiceImpl serviceUnderTest;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        Specialty specialty = buildSpecialty();
        when(specialtyRepository.save(any())).thenReturn(specialty);
        Specialty savedSpecialty = serviceUnderTest.save(specialty);
        assertEquals(specialty, savedSpecialty);
    }

    @Test
    void update() {
        Specialty specialty = buildSpecialty();
        serviceUnderTest.update(specialty);
        verify(specialtyRepository, times(1)).save(specialty);
    }

    @Test
    void deleteBy() {
        Specialty specialty = buildSpecialty();
        serviceUnderTest.deleteBy(specialty.getId());
        verify(specialtyRepository, times(1)).deleteById(specialty.getId());
    }

    @Test
    void getAll() {
        List<Specialty> specialties = Collections.singletonList(buildSpecialty());
        when(specialtyRepository.findAll()).thenReturn(specialties);
        List<Specialty> allSpecialties = serviceUnderTest.findAll();
        assertEquals(specialties, allSpecialties);
    }

    @Test
    void getById() {
        Optional<Specialty> specialty = Optional.of(buildSpecialty());
        when(specialtyRepository.findById(specialty.get().getId())).thenReturn(specialty);
        Optional<Specialty> specialtyById = serviceUnderTest.findById(specialty.get().getId());
        assertEquals(specialty, specialtyById);
    }

    @Test
    void saveIfAbsent() {
        Specialty specialty = buildSpecialty();
        when(specialtyRepository.save(any())).thenReturn(specialty);
        Specialty savedSpecialty = serviceUnderTest.save(specialty);
        assertEquals(specialty, savedSpecialty);
    }

    private Specialty buildSpecialty() {
        return new Specialty(4L, "random_name", "description");
    }
}
