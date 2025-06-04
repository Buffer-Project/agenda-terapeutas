package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Therapist;

import java.util.List;

public interface ITherapistService {
    Therapist createTherapist(Therapist therapist);

    Therapist updateTherapist(Therapist therapist) throws Exception;

    void deleteTherapistById(Long therapistId) throws Exception;

    Therapist getTherapistById(Long id) throws Exception;

    List<Therapist> getAllTherapists() ;
}
