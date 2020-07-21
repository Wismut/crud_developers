package ua.wismut.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import ua.wismut.model.Specialty;
import ua.wismut.service.SpecialtyService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecialtyControllerTest {
    @InjectMocks
    private SpecialtyController controllerUnderTest;

    @Mock
    private SpecialtyService specialtyService;

    @Mock
    private BindingResult bindingResult;

    @BeforeAll
    public static void setup() {
        MockitoAnnotations.initMocks(SpecialtyControllerTest.class);
    }

    @Test
    public void findAllSpecialtysFoundShouldReturnFoundSpecialtyEntries() throws Exception {
        Specialty firstSpecialty = buildSpecialty(1L, "Mike", "Halts");
        Specialty secondSpecialty = buildSpecialty(2L, "Deny", "Richy");

        when(specialtyService.findAll()).thenReturn(Arrays.asList(firstSpecialty, secondSpecialty));

        List<Specialty> specialties = controllerUnderTest.findAll();
        assertEquals(2, specialties.size());
        assertEquals(firstSpecialty, specialties.get(0));
        assertEquals(secondSpecialty, specialties.get(1));

        verify(specialtyService, times(1)).findAll();
        verifyNoMoreInteractions(specialtyService);
    }

    @Test
    public void findByIdShouldReturnFoundSpecialtyEntry() throws Exception {
        Specialty specialty = buildSpecialty();

        when(specialtyService.findById(specialty.getId())).thenReturn(Optional.of(specialty));

        Specialty foundedSpecialty = controllerUnderTest.findById(specialty.getId());
        assertEquals(specialty, foundedSpecialty);

        verify(specialtyService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(specialtyService);
    }

    @Test
    public void saveShouldReturnSavedSpecialtyEntry() throws Exception {
        Specialty specialty = buildSpecialty();

        when(specialtyService.save(specialty, bindingResult)).thenReturn(specialty);

        Specialty savedSpecialty = controllerUnderTest.save(specialty, bindingResult);
        assertEquals(specialty, savedSpecialty);

        verify(specialtyService, times(1)).save(any(), any());
        verifyNoMoreInteractions(specialtyService);
    }

    @Test
    public void updateShouldReturnUpdatedSpecialtyEntry() throws Exception {
        Specialty specialty = buildSpecialty();

        when(specialtyService.update(specialty, specialty.getId())).thenReturn(specialty);

        Specialty updatedSpecialty = controllerUnderTest.update(specialty, specialty.getId());
        assertEquals(specialty, updatedSpecialty);

        verify(specialtyService, times(1)).update(any(), anyLong());
        verifyNoMoreInteractions(specialtyService);
    }

    @Test
    public void deleteById() {
        Long specialtyId = 5L;

        controllerUnderTest.deleteById(specialtyId);

        verify(specialtyService, times(1)).deleteById(5L);
        verifyNoMoreInteractions(specialtyService);
    }

    private Specialty buildSpecialty() {
        Specialty specialty = new Specialty();
        specialty.setId(7L);
        specialty.setName("A");
        specialty.setDescription("B");
        return specialty;
    }

    private Specialty buildSpecialty(Long id, String name, String description) {
        Specialty specialty = new Specialty();
        specialty.setId(id);
        specialty.setName(name);
        specialty.setDescription(description);
        return specialty;
    }
}
