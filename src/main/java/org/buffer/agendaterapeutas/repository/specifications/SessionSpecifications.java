package org.buffer.agendaterapeutas.repository.specifications;

import org.buffer.agendaterapeutas.exception.SessionException;
import org.buffer.agendaterapeutas.model.entity.Session;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.buffer.agendaterapeutas.exception.errors.SessionError.UNMAPPED_PARAM;

public class SessionSpecifications {

    public static Specification<Session> getSessionSpec(String key, Object value){
        return switch (key) {
            case "startTime" -> startDateAfter((LocalDateTime) value);
            case "endTime" -> endDateBefore((LocalDateTime) value);
            default -> throw new SessionException(UNMAPPED_PARAM, key);
        };
    }

    public static Specification<Session> startDateAfter(LocalDateTime startTime){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("startDateTime"), startTime));
    }

    private static Specification<Session> endDateBefore(LocalDateTime endTime) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("endDateTime"), endTime));
    }
}
