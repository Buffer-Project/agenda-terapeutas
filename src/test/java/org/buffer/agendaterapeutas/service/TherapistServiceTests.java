package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.exception.TherapistException;
import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.exception.errors.TherapistError;
import org.buffer.agendaterapeutas.exception.errors.UserError;
import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.entity.User;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.ITherapistRepository;
import org.buffer.agendaterapeutas.repository.IUserRepository;
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
class TherapistServiceTests {


    @Mock
    ITherapistRepository therapistRepository;

    @Mock
    IUserRepository userRepository;

    @Mock
    IUserService userService;

    @InjectMocks
    TherapistServiceImpl therapistService;

    private TherapistVO getMockTherapistVO() {
        TherapistVO therapist = new TherapistVO();
        therapist.setId(1L);
        therapist.setUser(new UserVO());
        therapist.getUser().setId(1L);
        therapist.getUser().setActive(true);
        therapist.getUser().setFirstName("John");
        therapist.getUser().setLastName("Doe");
        therapist.setSpecialty("Cardiology");

        return therapist;
    }

    @BeforeEach
    void setup() {

    }


    @Test
    void createTherapistTest() {

        TherapistVO therapistVO = getMockTherapistVO();
        therapistVO.setId(null);
        Therapist therapistEntity = new Therapist(therapistVO);
        User user = new User();
        user.setId(1L);
        user.setActive(true);
        user.setFirstName("John");
        user.setLastName("Doe");
        therapistEntity.setUser(user);

        when(userService.existsById(therapistVO.getUser().getId())).thenReturn(true);
        when(therapistRepository.save(any(Therapist.class))).thenReturn(therapistEntity);

        TherapistVO result = therapistService.createTherapist(therapistVO);

        assertNotNull(result.getUser());

        verify(therapistRepository, times(1)).save(any(Therapist.class));
    }

    @Test
    void createTherapistWhenProvidedTherapistHasNullUserTest(){
        TherapistVO therapistVO = getMockTherapistVO();
        therapistVO.setUser(null);

        TherapistException result = assertThrows(TherapistException.class, () -> {
            therapistService.createTherapist(therapistVO);
        });

        assertEquals(result.getCode(), TherapistError.EMPTY_USER.getCode());
        assertEquals(TherapistException.class, result.getClass());
    }

    @Test
    void createTherapistWhenProvidedTherapistHasIdTest(){
        TherapistVO therapistVO = getMockTherapistVO();
        therapistVO.setId(1L);

        when(userService.existsById(therapistVO.getUser().getId())).thenReturn(true);

        TherapistException result = assertThrows(TherapistException.class, () -> {
            therapistService.createTherapist(therapistVO);
        });

        assertEquals(result.getCode(), TherapistError.INVALID_FORMAT.getCode());
        assertEquals(TherapistException.class, result.getClass());
    }


    @Test
    void createTherapistWhenItsUserNotExistsTest() {
        Long id = 43L;
        TherapistVO therapistVO = getMockTherapistVO();
        therapistVO.getUser().setId(id);

        when(userService.existsById(id)).thenReturn(false);

        UserException result = assertThrows(UserException.class, () -> {
            therapistService.createTherapist(therapistVO);
        });

        assertEquals(result.getCode(), UserError.NOT_FOUND.getCode());
        assertEquals(UserException.class, result.getClass());
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
        assert (result.getClass().equals(TherapistException.class));


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
        assert (result.getClass().equals(TherapistException.class));


    }

    @Test
    void updateTherapistTestWhenProvidedIdDoesNotEqualProvidedTherapistIdTest() {
        Long id = 700L;
        TherapistVO therapistVO = getMockTherapistVO();

        TherapistException result = assertThrows(TherapistException.class, () -> {
            therapistService.updateTherapist(therapistVO, id);
        });

        assertEquals(result.getCode(), TherapistError.ID_CONFLICT.getCode());
        assert (result.getClass().equals(TherapistException.class));

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
        assert (result.getClass().equals(TherapistException.class));
        verify(therapistRepository).existsById(id);
    }

    @Test
    void deleteTherapistByIdTest() {
        Long id = 3L;


        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setActive(true);


        Therapist therapist = new Therapist();
        therapist.setId(id);
        therapist.setUser(user);
        therapist.setSpecialty("Psychology");

        when(therapistRepository.findById(id)).thenReturn(Optional.of(therapist));

        therapistService.deleteTherapistById(id);

        assertFalse(therapist.getUser().isActive());

        verify(therapistRepository, times(1)).findById(id);
        verify(therapistRepository, times(1)).save(therapist);

    }

    @Test
    void deleteTherapistByIdWhenTherapistDoesNotExistTest() {
        Long id = 20L;

        when(therapistRepository.findById(id)).thenReturn(Optional.empty());

        TherapistException result = assertThrows(TherapistException.class, () -> {
            therapistService.deleteTherapistById(id);
        });

        assertEquals(result.getCode(), TherapistError.NOT_FOUND.getCode());
        assert (result.getClass().equals(TherapistException.class));
        verify(therapistRepository, times(1)).findById(id);


    }

    @Test
    void getTherapistByIdTest() {
        Long id = 1L;
        TherapistVO therapistVO = getMockTherapistVO();
        Therapist therapist = new Therapist(therapistVO);


        when(therapistRepository.findById(id)).thenReturn(Optional.of(therapist));

        TherapistVO result = therapistService.getTherapistById(id);

        assertEquals(therapistVO.getSpecialty(), result.getSpecialty());
        assertEquals(therapistVO.getId(), result.getId());

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
        assert (result.getClass().equals(TherapistException.class));
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
