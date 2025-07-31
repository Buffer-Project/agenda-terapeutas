package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.bo.TherapistBO;
import org.buffer.agendaterapeutas.model.entity.Therapist;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        return therapist;
    }

    @BeforeEach
    void setup() {

    }


    @Test
    public void createTherapistTest() {
        TherapistVO therapist = getMockTherapistVO();
        Therapist therapistEntity = new Therapist(new TherapistBO(therapist));
        therapistEntity.setId(1L);
        when(therapistRepository.save(any(Therapist.class))).thenReturn(therapistEntity);

        TherapistVO result = therapistService.createTherapist(therapist);

        assert (result.getId().equals(1L));
        verify(therapistRepository).save(any(Therapist.class));

    }


    @Test
    public void updateTherapistTest() {

    }

    @Test
    public void updateTherapistTestWhenProvidedIdNullOrProvidedTherapistIdIsNullTest() {

    }

    @Test
    public void updateTherapistTestWhenProvidedIdDoesNotEqualsProvidedTherapistIdTest() {

    }

    @Test
    public void updateTherapistTestWhenTherapistDoesNotExistTest() {

    }

    @Test
    public void deleteTherapistByIdTest() {

    }

    @Test
    public void deleteTherapistByIdWhenTherapistDoesNotExistTest() {

    }

    @Test
    public void getTherapistByIdTest() {

    }

    @Test
    public void getAllTherapistsTest() {

    }


}
