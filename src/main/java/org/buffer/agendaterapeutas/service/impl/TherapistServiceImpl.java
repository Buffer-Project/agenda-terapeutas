package org.buffer.agendaterapeutas.service.impl;

import org.buffer.agendaterapeutas.exception.TherapistException;
import org.buffer.agendaterapeutas.exception.UserException;
import org.buffer.agendaterapeutas.exception.errors.TherapistError;
import org.buffer.agendaterapeutas.exception.errors.UserError;
import org.buffer.agendaterapeutas.model.entity.Therapist;
import org.buffer.agendaterapeutas.model.entity.User;

import org.buffer.agendaterapeutas.model.vo.UserVO;
import org.buffer.agendaterapeutas.repository.ITherapistRepository;
import org.buffer.agendaterapeutas.service.ITherapistService;
import org.buffer.agendaterapeutas.model.vo.TherapistVO;
import org.buffer.agendaterapeutas.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TherapistServiceImpl implements ITherapistService {

    private final ITherapistRepository therapistRepository;
    private final IUserService userService;

    public TherapistServiceImpl(ITherapistRepository therapistRepository, IUserService userService) {
        this.therapistRepository = therapistRepository;
        this.userService = userService;
    }

    @Override
    public TherapistVO createTherapist(TherapistVO therapistVO) {

        Long userId = therapistVO.getUser().getId();
        UserVO userVO = userService.getUserById(userId);

        if (userVO == null) {
            throw new UserException(UserError.NOT_FOUND, userId);
        }
        therapistVO.setId(null);
        Therapist savedTherapist = therapistRepository.save(new Therapist(therapistVO));
        return new TherapistVO(savedTherapist);
    }

    @Override
    public TherapistVO updateTherapist(TherapistVO therapist, Long id) {

        if (id == null || therapist.getId() == null) {
            throw new TherapistException(TherapistError.MISSING_ID);
        }

        if (!therapist.getId().equals(id)) {
            throw new TherapistException(TherapistError.ID_CONFLICT);
        }

        if (!therapistRepository.existsById(id)) {
            throw new TherapistException(TherapistError.NOT_FOUND, id);
        }

        Therapist updatedTherapist = therapistRepository.save(new Therapist(therapist));
        return new TherapistVO(updatedTherapist);
    }

    @Override
    public void deleteTherapistById(Long id) {
        Optional<Therapist> therapist = therapistRepository.findById(id);
        if (therapist.isEmpty()) {
            throw new TherapistException(TherapistError.NOT_FOUND, id);
        }

        therapist.get().getUser().setActive(false);

        therapistRepository.save(therapist.get());
    }

    @Override
    public TherapistVO getTherapistById(Long id) {
        Optional<Therapist> therapist = therapistRepository.findById(id);
        if (therapist.isEmpty()) {
            throw new TherapistException(TherapistError.NOT_FOUND, id);
        }

        return new TherapistVO(therapist.get());
    }

    @Override
    public List<TherapistVO> getAllTherapists() {
        return therapistRepository.findAll()
                .stream()
                .map(TherapistVO::new)
                .toList();
    }


}
