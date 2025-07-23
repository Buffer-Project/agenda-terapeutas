package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;

import java.util.List;

public interface ITherapistService {
    TherapistVO createTherapist(TherapistVO therapist);

    TherapistVO updateTherapist(TherapistVO therapist,Long id) ;

    void deleteTherapistById(Long therapistId) ;

    TherapistVO getTherapistById(Long id);

    List<TherapistVO> getAllTherapists();


}
