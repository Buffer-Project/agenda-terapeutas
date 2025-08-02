package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.exception.TherapistException;
import org.buffer.agendaterapeutas.exception.errors.TherapistError;
import org.buffer.agendaterapeutas.model.bo.TherapistBO;
import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.vo.SpecialtyVO;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.ITherapistRepository;
import org.buffer.agendaterapeutas.service.impl.TherapistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TherapistServiceTests {


    @Mock
    ITherapistRepository therapistRepository;

    @InjectMocks
    TherapistServiceImpl therapistService;

    private TherapistVO getMockTherapistVO() {
        TherapistVO therapist = new TherapistVO();
        therapist.setId(1L);
        therapist.setUser(new UserVO());
        therapist.getUser().setFirstName("John");
        therapist.getUser().setLastName("Doe");
        therapist.setSpecialty(new SpecialtyVO(1L, "Cardiology"));
        return therapist;
    }

    @BeforeEach
    void setup() {

    }


    @Test
    void createTherapistTest() {
        TherapistVO therapist = getMockTherapistVO();
        Therapist therapistEntity = new Therapist(new TherapistBO(therapist));
        therapistEntity.setId(1L);
        when(therapistRepository.save(any(Therapist.class))).thenReturn(therapistEntity);

        TherapistVO result = therapistService.createTherapist(therapist);

        assert (result.getId().equals(1L));
        verify(therapistRepository, times(1)).save(any(Therapist.class));

    }


    @Test
    void updateTherapistTest() {
        Long id = 1L;
        TherapistVO therapistVO = getMockTherapistVO();
        therapistVO.setId(1L);

        Therapist updatedTherapistEntity = new Therapist(therapistVO);
        updatedTherapistEntity.setId(1L);

        when(therapistRepository.existsById(id)).thenReturn(true);
        when(therapistRepository.save(any(Therapist.class))).thenReturn(updatedTherapistEntity);

        TherapistVO result = therapistService.updateTherapist(therapistVO, id);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(therapistRepository).existsById(id);
        verify(therapistRepository).save(any(Therapist.class));
    }

    @Test
    void updateTherapistTestWhenProvidedIdNullTest() {
        Long id = null;
        TherapistVO therapistVO = getMockTherapistVO();


        TherapistException result = assertThrows(TherapistException.class, () -> {
            therapistService.updateTherapist(therapistVO, id);
        });

        assert (result.getCode() == TherapistError.MISSING_ID.getCode());
        assert(result.getClass().equals(TherapistException.class));

    }

    @Test
    void updateTherapistWhenProvidedTherapistObjectIdIsNull() {
        Long id = 50L;


        TherapistVO therapistVO = getMockTherapistVO();
        therapistVO.setId(null);

        TherapistException result = assertThrows(TherapistException.class, () -> {
            therapistService.updateTherapist(therapistVO, id);
        });

        assert (result.getCode() == TherapistError.MISSING_ID.getCode());
        assert(result.getClass().equals(TherapistException.class));

    }

    @Test
    void updateTherapistTestWhenProvidedIdDoesNotEqualProvidedTherapistIdTest() {
        Long id = 700L;
        TherapistVO therapistVO = getMockTherapistVO();
        Therapist therapistEntity = new Therapist(therapistVO);

        TherapistException result = assertThrows(TherapistException.class, () -> {
            therapistService.updateTherapist(therapistVO, id);
        });

        assertEquals(result.getCode(), TherapistError.ID_CONFLICT.getCode());
        assert(result.getClass().equals(TherapistException.class));

    }

    @Test
    void updateTherapistTestWhenTherapistDoesNotExistTest() {
        Long id = 1L;
        TherapistVO therapistVO = getMockTherapistVO();
        when(therapistRepository.existsById(id)).thenReturn(false);

        TherapistException result = assertThrows(TherapistException.class, () -> {
            therapistService.updateTherapist(therapistVO, id);
        });

        assertEquals(result.getCode(), TherapistError.NOT_FOUND.getCode());
        assert(result.getClass().equals(TherapistException.class));
        verify(therapistRepository).existsById(id);
    }

    @Test
    void deleteTherapistByIdTest() {
        Long id = 3L;

        TherapistVO therapistVO = getMockTherapistVO();
        Therapist therapist = new Therapist(therapistVO);


        when(therapistRepository.findById(id)).thenReturn(Optional.of(therapist));

        therapistService.deleteTherapistById(id);

        verify(therapistRepository, times(1)).findById(id);
        verify(therapistRepository, times(1)).deleteById(id);

    }

    @Test
    void deleteTherapistByIdWhenTherapistDoesNotExistTest() {
        Long id = 20L;

        when(therapistRepository.findById(id)).thenReturn(Optional.empty());

        TherapistException result = assertThrows(TherapistException.class, () -> {
            therapistService.deleteTherapistById(id);
        });

        assertEquals(result.getCode(), TherapistError.NOT_FOUND.getCode());
        assert(result.getClass().equals(TherapistException.class));
        verify(therapistRepository, times(1)).findById(id);


    }

    @Test
    void getTherapistByIdTest() {
        Long id = 1L;
        TherapistVO therapistVO = getMockTherapistVO();
        Therapist therapist = new Therapist(therapistVO);

        when(therapistRepository.findById(id)).thenReturn(Optional.of(therapist));

        TherapistVO result = therapistService.getTherapistById(id);

        assertEquals("John", result.getUser().getFirstName());
        verify(therapistRepository, times(1)).findById(id);

    }

    @Test
    void getTherapistByIdWhenTherapistDoesNotExistTest() {

        Long id = 500L;
        when(therapistRepository.findById(id)).thenReturn(Optional.empty());

        TherapistException result = assertThrows(TherapistException.class, () -> {
            therapistService.getTherapistById(id);
        });

        assertEquals(result.getCode(), TherapistError.NOT_FOUND.getCode());
        assert(result.getClass().equals(TherapistException.class));
        verify(therapistRepository, times(1)).findById(id);

    }

    @Test
    void getAllTherapistsTest() {

        List<Therapist> therapists = new ArrayList<>();
        therapists.add(new Therapist(getMockTherapistVO()));
        therapists.add(new Therapist(getMockTherapistVO()));
        therapists.add(new Therapist(getMockTherapistVO()));

        when(therapistRepository.findAll()).thenReturn(therapists);

        List<TherapistVO> result = therapistService.getAllTherapists();

        assertEquals(3, result.size());

        verify(therapistRepository, times(1)).findAll();


    }


}
