package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Therapist;

public interface ITherapistService {
    Therapist createTherapist(Therapist therapist);

    Therapist updateTherapist(Therapist therapist);

    void deleteTherapistById(Long therapistId);

    Therapist getTherapistById(Long id) throws Exception;
}
