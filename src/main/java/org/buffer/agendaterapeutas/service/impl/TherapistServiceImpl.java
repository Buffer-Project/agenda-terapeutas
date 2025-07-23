package org.buffer.agendaterapeutas.service.impl;

import org.buffer.agendaterapeutas.exception.SchedulerException;
import org.buffer.agendaterapeutas.exception.TherapistNotFoundException;
import org.buffer.agendaterapeutas.model.bo.SessionBO;
import org.buffer.agendaterapeutas.model.bo.TherapistBO;
import org.buffer.agendaterapeutas.model.entity.Session;
import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.entity.User;
import org.buffer.agendaterapeutas.model.vo.SessionVO;
import org.buffer.agendaterapeutas.repository.ISessionRepository;
import org.buffer.agendaterapeutas.repository.ITherapistRepository;
import org.buffer.agendaterapeutas.service.ITherapistService;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TherapistServiceImpl implements ITherapistService {

    private final ITherapistRepository therapistRepository;
    private final ISessionRepository sessionRepository;

    public TherapistServiceImpl(ITherapistRepository therapistRepository, ISessionRepository sessionRepository) {
        this.therapistRepository = therapistRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public TherapistVO createTherapist(TherapistVO therapistVO) {

        Therapist therapist = new Therapist();

        User user = new User();

        user.setFirstName(therapistVO.getUser().getFirstName());
        user.setLastName(therapistVO.getUser().getLastName());
        user.setUsername(therapistVO.getUser().getUsername());

        therapist.setId(therapistVO.getId());
        therapist.setUser(user);

        Therapist savedTherapist = therapistRepository.save(therapist);
        return new TherapistVO(new TherapistBO(savedTherapist));
    }

    @Override
    public TherapistVO updateTherapist(TherapistVO therapist, Long id) {

        if (id == null) {
            throw new TherapistNotFoundException("El ID del terapeuta no puede ser null");
        }

        if (therapist.getId() == null) {
            throw new TherapistNotFoundException("El ID del terapeuta en el objeto no puede ser null");
        }


        if (!therapist.getId().equals(id)) {
            throw new TherapistNotFoundException("El ID del terapeuta no coincide con el ID de la URL");
        }


        if (!therapistRepository.existsById(id)) {
            throw new TherapistNotFoundException("Terapeuta no encontrado con ID: " + id);
        }


        TherapistBO therapistBO = new TherapistBO(therapist);
        Therapist updatedTherapist = therapistRepository.save(new Therapist(therapistBO));
        return new TherapistVO(new TherapistBO(updatedTherapist));
    }

    @Override
    public void deleteTherapistById(Long id) {
        Optional<Therapist> therapist = therapistRepository.findById(id);
        if (therapist.isEmpty()) {
            throw new TherapistNotFoundException();
        }
        therapistRepository.deleteById(id);
    }

    @Override
    public TherapistVO getTherapistById(Long id) {
        Optional<Therapist> therapist = therapistRepository.findById(id);
        if (therapist.isEmpty()) {
            throw new TherapistNotFoundException();
        }
        TherapistBO therapistBO = new TherapistBO(therapist.get());
        return new TherapistVO(therapistBO);
    }

    @Override
    public List<TherapistVO> getAllTherapists() {
        return therapistRepository.findByUserActiveTrue()
                .stream()
                .map(t -> new TherapistVO(new TherapistBO(t)))
                .toList();
    }


}
