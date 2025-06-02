package org.buffer.agendaterapeutas.service;

import org.buffer.agendaterapeutas.model.Therapist;
import org.buffer.agendaterapeutas.repository.TherapistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TherapistService {

    private final TherapistRepository therapistRepository;

    public TherapistService(TherapistRepository therapistRepository) {
        this.therapistRepository = therapistRepository;
    }

    public Therapist createTherapist(Therapist therapist) {
        return therapistRepository.save(therapist);
    }

    public Therapist updateTherapist(Therapist therapist) throws Exception {
        Long id = therapist.getId();
        if (id == null || !therapistRepository.existsById(id)) {
            throw new Exception("Therapist not found with id: " + id);
        }
        return therapistRepository.save(therapist);
    }

    public void deleteTherapistById(Long id) throws Exception {
        if (!therapistRepository.existsById(id)) {
            throw new Exception("Therapist not found with id: " + id);
        }
        therapistRepository.deleteById(id);
    }

    public Therapist getTherapistById(Long id) throws Exception {
        return therapistRepository.findById(id)
                .orElseThrow(() -> new Exception("Therapist not found with id: " + id));
    }

    public List<Therapist> getAllTherapists() {
        return therapistRepository.findAll();
    }
}
